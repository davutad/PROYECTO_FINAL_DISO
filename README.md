# Proyecto Final DISO - Sistema de reparto de comida tipo Uber Eats

## Descripción
Simulación de una plataforma de comida a domicilio inspirada en Uber Eats.

## Patrones utilizados

### Factory Method
Se usa para crear distintos tipos de usuarios: cliente, restaurante y repartidor.

### Observer
Se usa para notificar cambios de estado del pedido.

### State
Se usa para representar el ciclo de vida del pedido.

### Strategy
Se usa para seleccionar el método de pago.

### Decorator
Se usa para personalizar productos del menú.

## Flujo principal
1. El cliente crea un pedido.
2. El restaurante recibe el pedido.
3. El pedido cambia de estado.
4. El repartidor lo entrega.
5. Los observadores reciben notificaciones.
