# Shift Browser Technical Challenge

## Install dependencies

1. Install WinAppDriver - https://github.com/Microsoft/WinAppDriver/releases
2. Install Node.js ltm (22.20.0)
3. Install NVM (and NPM should be included)
4. `npm install -g appium`
5. `appium driver install windows`
5. `appium --address 127.0.0.1 --port 4725`

## Run Test
1. `mvn clean install -DskipTests=true`
2. `mvn test`

or in Intellij:
1. right click testng.xml
2. run tests

## Tech Stack

- Java 21
- Maven
- Selenium
- TestNg
- Appium

## Test Architecture 

1. Before Suite:
   2. Deletes existing installation files 
   3. Start Firefox and download Shift installation files
   3. Start Appium session
   3. Install Shift and go through Shift Setup screen
   4. Closes App
5. After Suite:
   6. Uninstall Shift 
   7. Delete installation files 
8. Test01_CheckVersionFromMetaDataTest
   9. uses PowerShell util to retrieve version from metadata 
10. Test02_CheckVersionOfInstalledBrowserTest
    11. Starts a new browser appium session
    12. navigates to browser settings and retrieves version from page and validates against installation metadata version
13. Test03_BrowserNavigationTest
    14. Starts a new browser appium session
    15. navigates to redbrick website and checks page text to verify role is still available
    16. (if time permitted I would try to find text in particular block rather than get text from whole page)
17. Test04_SaveBookmarksTest
    18. Starts a new browser appium session
    19. (if time permitted I would firstly delete previous bookmarks if present)
    20. Navigates to career website and saves bookmark
    21. checks bookmark setting page shows the bookmark 
    22. After class will delete the bookmark (if time permitted rather than staying on page, probably better to re-navigate to this page in a new session)


