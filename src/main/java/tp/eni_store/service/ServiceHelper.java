package tp.eni_store.service;

public class ServiceHelper {
    /**
     * Utilitaire pour centraliser la construction d'un service response
     * @param code code métier
     * @param data
     * @return
     * @param <T>
     */
    static <T> ServiceResponse<T> buildResponse(int code, T data){
        ServiceResponse<T> serviceResponse = new ServiceResponse<T>();

        serviceResponse.code = code;
        serviceResponse. data = data;

        System.out.println(String.format("Service response : code=%s", code));

        return serviceResponse;
    }

    static <T> ServiceResponse<T> buildResponse(int code){
        return buildResponse(code, null);
    }
}
