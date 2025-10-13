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

### Before Suite

1. Deletes existing installation files
2. Starts **Firefox** and downloads the Shift installation files
3. Starts **Appium session**
4. Installs **Shift** and goes through the setup screen
5. Closes the app

---

### After Suite

1. Uninstalls **Shift**
2. Deletes installation files

---

### Test Cases

#### **Test01 — Check Version from Metadata**
- Uses **PowerShell utility** to retrieve the version from the installer metadata.

---

#### **Test02 — Check Version of Installed Browser**
- Starts a **new Appium browser session**
- Navigates to browser **settings**
- Retrieves version from the settings page
- Validates it against the **installation metadata version**

---

#### **Test03 — Browser Navigation Test**
- Starts a **new Appium browser session**
- Navigates to the **Redbrick website**
- Verifies that the **“Role Available”** text appears on the page
- _If time permitted_: refine validation to look for text within a specific content block instead of the whole page

---

#### **Test04 — Save Bookmarks Test**
- Starts a **new Appium browser session**
- _If time permitted_: delete previous bookmarks if present
- Navigates to the **career website** and saves a bookmark
- Verifies that the **bookmark appears** in the settings page
- _After class_: delete the bookmark (ideally by starting a new session rather than staying on the same page)

---

### Summary

This suite ensures:
- The **Shift browser installs and uninstalls cleanly**
- Version consistency between **metadata** and **installed application**
- Core browser functionality such as **navigation** and **bookmarking** works correctly

---

**Author:** Amrik Singh Samra  
