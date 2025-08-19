package tp.eni_store.service;

public class ServiceResponse<T> {
    public int code;
    public T data = null;
    public String message = null;
}
