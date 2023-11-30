package sessionFramework.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Security {
	private static Security instance;
	private Map<Integer, Map<String, Map<String, List<String>>>> permissions = new HashMap<>();

	public static Security getInstance() {
		if (instance == null) {
	        synchronized (Security.class) {
	            if (instance == null) {
	                instance = new Security();
	            }
	        }
	    }
	    return instance;
	}
	
	private Security() {
		
	}
	
	public Map<Integer, Map<String, Map<String, List<String>>>> getPermissions() {
		return permissions;
	}
	
    public void addPermission(Integer profile, String module, String object, String method) {
        
    	//Aqui verificamos si el perfil ya existe en el mapa de permisos
        Map<String, Map<String, List<String>>> moduleByObject = permissions.get(profile);
        if (moduleByObject == null) {
            moduleByObject = new HashMap<>();
            permissions.put(profile, moduleByObject);
        }

        //Verificamos si el módulo ya existe en el mapa de objetos
        Map<String, List<String>> objectByMethod = moduleByObject.get(module);
        if (objectByMethod == null) {
            objectByMethod = new HashMap<>();
            moduleByObject.put(module, objectByMethod);
        }

        //Volvemos a verificar si el objeto ya existe en el mapa de métodos
        List<String> methods = objectByMethod.get(object);
        if (methods == null) {
            methods = new ArrayList<>();
            objectByMethod.put(object, methods);
        }

        //Se agrega el metodo a la lista de metodo del objecto si no existe
        if (!methods.contains(method)) {
            methods.add(method);
        }
    }
    
    public boolean hasPermission(Integer profile, String module, String object, String method) {
        // Verificar si el perfil tiene permisos asignados
        if (!permissions.containsKey(profile)) {
            return false;
        }

        // Obtener el mapa de módulos y objetos asociados al perfil
        Map<String, Map<String, List<String>>> moduleByObject = permissions.get(profile);

        // Verificar si el módulo tiene permisos asignados
        if (!moduleByObject.containsKey(module)) {
            return false;
        }

        // Obtener el mapa de objetos y métodos asociados al módulo
        Map<String, List<String>> objectByMethod = moduleByObject.get(module);

        // Verificar si el objeto tiene permisos asignados
        if (!objectByMethod.containsKey(object)) {
            return false;
        }

        // Obtener la lista de métodos asociados al objeto
        List<String> methods = objectByMethod.get(object);

        // Verificar si el método está en la lista de métodos permitidos
        return methods.contains(method);
    }
    
    public void removePermissions(Integer profile, Module module, Object object, Method method) {
        // Verificar si el perfil tiene permisos asignados
        if (!permissions.containsKey(profile)) {
            return;
        }

        // Obtener el mapa de módulos y objetos asociados al perfil
        Map<String, Map<String, List<String>>> moduleByObject = permissions.get(profile);

        // Verificar si el módulo tiene permisos asignados
        if (!moduleByObject.containsKey(module)) {
            return;
        }

        // Obtener el mapa de objetos y métodos asociados al módulo
        Map<String, List<String>> objectByMethod = moduleByObject.get(module);

        // Verificar si el objeto tiene permisos asignados
        if (!objectByMethod.containsKey(object)) {
            return;
        }

        // Obtener la lista de métodos asociados al objeto
        List<String> methods = objectByMethod.get(object);

        // Remover el método de la lista de métodos permitidos
        methods.remove(method);

        // Si la lista de métodos está vacía, remover el objeto del mapa de objetos
        if (methods.isEmpty()) {
            objectByMethod.remove(object);
        }

        // Si el mapa de objetos está vacío, remover el módulo del mapa de módulos
        if (objectByMethod.isEmpty()) {
            moduleByObject.remove(module);
        }

        // Si el mapa de módulos está vacío, remover el perfil del mapa de permisos
        if (moduleByObject.isEmpty()) {
            permissions.remove(profile);
        }
    }
//    public ResponseEntity<String> getPermissionsAsJson() {
//        StringBuilder jsonBuilder = new StringBuilder();
//        jsonBuilder.append("{");
//
//        for (Map.Entry<Integer, Map<String, Map<String, List<String>>>> profileEntry : permissions.entrySet()) {
//            Integer profile = profileEntry.getKey();
//            Map<String, Map<String, List<String>>> moduleMap = profileEntry.getValue();
//
//            jsonBuilder.append("\"profile\":{");
//            jsonBuilder.append("\"id\":").append(profile).append(",");
//            // Agrega otras propiedades del perfil
//
//            jsonBuilder.append("\"modules\":{");
//
//            for (Map.Entry<String, Map<String, List<String>>> moduleEntry : moduleMap.entrySet()) {
//                Module module = moduleEntry.getKey();
//                Map<Object, List<Method>> objectMap = moduleEntry.getValue();
//
//                jsonBuilder.append("\"").append(module.getName()).append("\":{");
//
//                for (Map.Entry<Object, List<Method>> objectEntry : objectMap.entrySet()) {
//                    Object object = objectEntry.getKey();
//                    List<Method> methods = objectEntry.getValue();
//
//                    jsonBuilder.append("\"").append(object.getName()).append("\":{");
//
//                    jsonBuilder.append("\"methods\":[");
//                    for (Method method : methods) {
//                        jsonBuilder.append("\"").append(method.getName()).append("\",");
//                    }
//                    if (!methods.isEmpty()) {
//                        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Elimina la última coma
//                    }
//                    jsonBuilder.append("]");
//
//                    jsonBuilder.append("},");
//                }
//                
//                if (!objectMap.isEmpty()) {
//                    jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Elimina la última coma
//                }
//
//                jsonBuilder.append("},");
//            }
//
//            if (!moduleMap.isEmpty()) {
//                jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Elimina la última coma
//            }
//
//            jsonBuilder.append("}");
//
//            jsonBuilder.append("},");
//        }
//
//        if (!permissions.isEmpty()) {
//            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Elimina la última coma
//        }
//
//        jsonBuilder.append("}");
//
//        String json = jsonBuilder.toString();
//
//        return new ResponseEntity<>(json, HttpStatus.OK);
//    }
    
    public java.lang.Object executeMethod(String module,String object, String method, java.lang.Object[] params) {
        try {
            // Cargamos la clase
            Class<?> myClass = Class.forName("sessionFramework."+ module +"." + object);
            
            // Creamos un arreglo de clases para representar los tipos de los parámetros
            Class<?>[] paramTypes = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                Class<?> type = params[i].getClass();
                if (type.equals(Integer.class)) {
                    paramTypes[i] = int.class;
                } else if (type.equals(Double.class)) {
                    paramTypes[i] = double.class;
                } else if (type.equals(Float.class)) {
                    paramTypes[i] = float.class;
                } else if (type.equals(Long.class)) {
                    paramTypes[i] = long.class;
                } else if (type.equals(Short.class)) {
                    paramTypes[i] = short.class;
                } else if (type.equals(Byte.class)) {
                    paramTypes[i] = byte.class;
                } else if (type.equals(Character.class)) {
                    paramTypes[i] = char.class;
                } else if (type.equals(Boolean.class)) {
                    paramTypes[i] = boolean.class;
                } else {
                    paramTypes[i] = type;
                }
            }

            // Instanciamos la clase
            java.lang.Object instance = myClass.getDeclaredConstructor().newInstance();

            // Obtenemos el método y le indicamos los tipos de los parámetros
            java.lang.reflect.Method exeMethod = myClass.getMethod(method, paramTypes);

            // Llamamos al método para ejecutarlo y recibir su retorno
            java.lang.Object result = (java.lang.Object) exeMethod.invoke(instance, params);
            return result;
        } catch (Exception e) {
            System.out.println("Hubo un error al intentar obtener la clase o el método:  "+ e);
            return null;
        }
    }
	
}
