# Callcenter
Solución de prueba de call center.

## Consigna
Existe un call center donde hay 3 tipos de empleados: operador, supervisor y director. El proceso de la atención de una llamada telefónica en primera instancia debe ser atendida por un operador, si no hay ninguno libre debe ser atendida por un supervisor, y de no haber tampoco supervisores libres debe ser atendida por un director.


## Clases
- **CallCenter.java:** Contiene el método *main* quien inicia la ejecución del programa. 
- **Dispatcher.java:** Es la encargada de manejar las llamadas.
- **Call.java:** Representa el objeto llamada.
- **Employee.java:** Representa el objeto empleado.
- **EmployeeType.java:** Representa el tipo de empleado (operador, supervisor y/o director).


## Test

- **dispatcherCallWithConfigurationClass:** Ejecuta el proceso con la configuración por defecto definida dentro del código.
- **dispatcherCallTestWithMoreCallsThanEmployees:** Ejecuta el proceso con mayor cantidad de llamadas que empleados. En este caso se están configurando 3 empleados (un operador, un supervisor y un director) pero ingresan 10 llamadas. La idea de la prueba es que las 3 primeras llamadas sean atendidas por los empleados en el orden correcto y las restantes esperen a que algún empleado se desocupe. Cuando esto suceda, al empleado se le asigna otra llamada y así sucesivamente hasta atender todas las llamadas.
- **dispatcherCallTestWithMoreEmployeesThanCalls:** Ejecuta el proceso con mayor cantidad de empleados que llamadas entrantes. En este caso se están configurando 10 empleados (8 operadores, un supervisor y un director) pero ingresan únicamente 7 llamadas. La idea de la prueba es que las 7 llamadas sean atendidas por 7 operadores (por orden de prioridad) y los otros empleados nunca se ocupen.
- **findEmployeeByPriorityAndAvailability:** Ejecuta el método que realiza la asignación de empleados por prioridad. Se configuran 3 empleados (1 operador, 1 supervisor y 1 director). Se busca el primer empleado disponible, ocupa y retorna el empleado tipo *operador*. Se busca otro empleado disponible y como el empleado *operador* está ocupado, ocupa y asigna el empleado tipo *supervisor*. Se busca otro empleado disponible y como el empleado *operador* y el tipo *supervisor* están ocupados, ocupa y asigna el empleado tipo *director*. 

## Extras

1. La solución que se da cuando hay una llamada y no hay un empleado libre se puede visualizar en el método *getAvailableEmployee* de la clase *Dispatcher.java*. Este método ejecuta un ciclo de tipo **do - while** para que busque si hay algún empleado disponible. El proceso queda dentro del ciclo hasta que algún empleado este disponible para atender la llamada.
2. Se agregaron varios test que permitían emular a ejecución en varios escenarios.
3. Se agrega documentación a cada método y clase explicando su funcionamiento.
