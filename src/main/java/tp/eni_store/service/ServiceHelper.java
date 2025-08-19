package tp.eni_store.service;

public class ServiceHelper {
    /**
     * Utilitaire pour centraliser la construction d'un service response
     * @param code code métier
     * @param data
     * @return
     * @param <T>
     */
    static <T> ServiceResponse<T> buildResponse(int code, T data, String message){
        ServiceResponse<T> serviceResponse = new ServiceResponse<T>();

        serviceResponse.code = code;
        serviceResponse. data = data;
        serviceResponse.message = message;

        System.out.println(String.format("Service response : code=%s", code));

        return serviceResponse;
    }

    static <T> ServiceResponse<T> buildResponse(int code){
        return buildResponse(code, null, null);
    }
}
