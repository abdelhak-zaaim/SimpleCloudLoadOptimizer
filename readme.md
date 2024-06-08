# Load Balancer

This is a simple load balancer simulation project implemented in Java using Maven for dependency management.

## Project Structure

The project is structured into several classes, each with a specific role:

- `Cloudlet.java`: This class represents a cloudlet, a task with a certain length and deadline that needs to be processed.
- `VirtualM.java`: This class represents a virtual machine that can process cloudlets.
- `LoadBalancer.java`: This class represents the load balancer, which is responsible for distributing the cloudlets among the available virtual machines.
- `CloudletHandler.java`: This class is responsible for handling incoming cloudlets and adding them to the load balancer.
- `Test.java`: This class is used to test the load balancer by sending it cloudlets.

## Getting Started

### Prerequisites

- Java 17
- Maven

### Building

To build the project, navigate to the project directory and run the following command:

```bash
mvn clean install
```

### Running

To run the project, execute the following command:

```bash
java -cp target/loadBalancing-1.0-SNAPSHOT.jar com.load.balancer.LoadBalancer
```
To run the test client, use the following command:

```bash
java -cp target/loadBalancing-1.0-SNAPSHOT.jar com.load.balancer.Test
```