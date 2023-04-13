In this repository we keep the current state of development of our Ticket Service. The Ticket Service is a Java-based API for our ticketing system, built with Maven and Docker for easy deployment and scalability. The project is built using Java 11 and the web core framework. We have a heap of documentation available for the code in this repository. You may be interested in...
* [Issues](https://github.com/project-dynasty/ticket-service/issues) if you want to request a feature or report a bug.
* [Code of Conduct](https://github.com/project-dynasty/docs/blob/main/docs/code_of_conduct.md)
* [Contributing Guidelines](https://github.com/project-dynasty/docs/blob/main/docs/contributing.md)

## Requirements

- Java 11 or later
- Apache Maven 3.6.3 or later

## Installation

1. Clone the repository.
2. Run `mvn clean install` to build the application.
3. Start the application using `java -jar "file-name"`.

## Branch guide
* `main`: The current main stage with the latest development version.
* `prod`: Production build (automatically deployed to the appropriate services)
* `release/*`: Release builds (e.g. `release/2023.2.1`, a stable running version named after the date)
* `fix/*`: Bug fix Branch (e.g. `fix/hash-bug`, for bug fixes)
* `feature/*`: Feature Update Branch (e.g. `feature/hash-update`, for large updates)
* `dev/*`: Developer specific branch (e.g. `dev/nicokempe`, designed for small changes and only temporary)

## Credits
* [JSON Web Tokens](https://jwt.io/)
