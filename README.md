Shift Browser Technical Challenge

Install dependencies and run appium

1. Install WinAppDriver - https://github.com/Microsoft/WinAppDriver/releases
2. `mvn clean install -DskipTests=true`
2. Install Node.js ltm (22.20.0)
3. Install NVM (and NPM should be included)
4. `npm install -g appium`
5. `appium driver install windows`
5. `appium --address 127.0.0.1 --port 4725`
6. `mvn test`

Tech Stack

- Java 21
- Maven
- Selenium
- TestNg
- Appium
  


