package com.ubereats;

public abstract class UserFactory {

    public User registerUser(String name, String location) {
        // TODO no estoy seguro si es buena idea que tenga parametros esto o deberia crear usuarios vacios y luego configurar cada uno, pero por ahora lo dejo asi
        User u = createUser(name, location);
        // TODO aqui va la configuracion extra concreta de cada tipo de usuario, y hay que agregar los observers de pedidos
        return u;
    }

    protected abstract User createUser(String name, String location);
}