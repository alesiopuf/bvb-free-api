# Bucharest Stock Exchange API (BVB) Simulation App

## Overview

This Java application serves as a simulated trading platform for the Bucharest Stock Exchange (BVB). It allows users to engage in mock trades, providing a platform for practicing trading strategies and understanding market dynamics without real financial risk. The application includes automatic price updates, typically occurring on an hourly basis, to simulate real-time market conditions.

## Setup Instructions

1. **Clone the Repository:**
`git clone https:/github.com/alesiopuf/bvb-free-api`

3. **Dependencies:**
- Ensure you have Java 17 and MySQL 8 installed on your system.
- This project may use external libraries for functionalities like HTTP requests or JSON parsing. Refer to the `pom.xml` file or any dependency management files for specifics.

3. **Configuration:**
- Review and configure any necessary settings (your database credential should be in `application.properties`)
- Modify the frequency of price updates in the code if needed (also `application.properties`)

4. **Build the Application:**
- Run this Maven command to build the application
`mvn clean install`

5. **Run the Application:**
- Execute the built application JAR file or run it directly from an IDE.

6. **Usage:**
- Upon launching the application, users can interact with the simulated trading platform via these endpoints (http://localhost:8080/swagger-ui/index.html)
- Perform trades, observe market behavior, and analyze trading strategies.
- Utilize the automatic price update feature to reflect real-time market fluctuations.

## Contribution Guidelines

- Contributions are welcome from anyone interested in improving the application.
- Contribution avenues include:
- Writing tests to ensure application stability and reliability.
- Enhancing specifications for better understanding and maintainability.
- Identifying and resolving bugs to improve the overall quality.
- All pull requests must undergo review by the repository owner before merging.

## Contact Information

- For inquiries or support related to the BVB Free API Simulation App, please contact:
- Email: alesiopuf@yahoo.com
- Email title: 'BVB Free API Support'

---

Ensure to follow the setup instructions carefully to deploy the application successfully. Your feedback and contributions are greatly appreciated for enhancing the functionality and robustness of the simulated trading platform.


