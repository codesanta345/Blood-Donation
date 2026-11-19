# Blood Donation

A desktop app for hospitals and blood banks to keep donor records, record donations, issue blood and see how much of each blood group is in stock. All history can be exported to CSV, which opens in Excel.

**Built with:** Java 17+, JavaFX 21, MySQL 8, Maven

Default login: **kirti / 123** (change it in `db.properties`)

---

## 1. Install these (once)

| What | Where | Note |
|---|---|---|
| **JDK 17 or newer** | https://adoptium.net (Temurin) | Tick "Set JAVA_HOME" in the installer |
| **MySQL Server 8 + MySQL Workbench** | https://dev.mysql.com/downloads/installer/ | Remember the **root password** you set |
| **An IDE** | IntelliJ IDEA Community, Eclipse IDE for Java Developers, or VS Code with the "Extension Pack for Java" | Maven is built into all of them |

You do **not** need to download JavaFX or the MySQL driver jar. Maven downloads them for you on the first run, so the first run needs internet.

## 2. Create the database

1. Open **MySQL Workbench** and connect to your local server.
2. File > Open SQL Script > choose `blood_donor_database.sql` from this folder.
3. Click the lightning-bolt button to run everything.
4. The output at the bottom should show one row of zeros (the blood stock).

## 3. Put in your MySQL password

Open `db.properties` in the project folder and change:

```
db.password=CHANGE_ME_TO_YOUR_MYSQL_PASSWORD
```

to your real MySQL root password. You can also change the app login here.

## 4. Run the app

**IntelliJ IDEA:** File > Open > select this folder (the one with `pom.xml`) > Trust project. Wait for Maven to finish loading. Then open `src/application/Launcher.java` and click the green ▶ next to `main`.

**Eclipse:** File > Import > Maven > Existing Maven Projects > choose this folder > Finish. Then right-click `src/application/Launcher.java` > Run As > Java Application.

**VS Code:** File > Open Folder > this folder. Open `src/application/Launcher.java` and click "Run" above `main`.

**Command line** (needs Maven installed): in this folder run

```
mvn javafx:run
```

The login window opens. Sign in, and the main panel with six buttons appears.

> Always run from the **project folder**. That's where the app looks for `db.properties`, saves donor photos (`photos/`) and writes exported CSV files.

---

## Problems?

| Message | Fix |
|---|---|
| `Access denied for user 'root'@'localhost'` | Wrong password in `db.properties` |
| `Communications link failure` | MySQL isn't running. Start it from Windows Services (`MySQL80`) or MySQL Workbench |
| `Unknown database 'blood_donor_databse'` | Run step 2 (the database name really is spelled "databse") |
| `Unknown column 'Op'` or `'units'` | You have the old database. Run the `ALTER TABLE` lines at the bottom of the SQL file |
| `JavaFX runtime components are missing` | Run `application.Launcher`, not `Login.Main` |
| `db.properties not found` in the console | The IDE is running from a different folder. Set the run configuration's working directory to the project folder |

---

## How to use it

1. **Register Donor:** fill in the form, pick a JPG photo and click Register. Find, Update and Delete work by mobile number.
2. **Donate:** pick the donor's mobile, click Search (this loads their blood group and photo), choose the date and click Upload. Stock goes up by 1.
3. **Issue Blood:** enter the patient details, blood group, units and date. Stock goes down. You get a message if there isn't enough.
4. **Available:** current units of A+, A-, B+, B-, AB+, AB-, O+ and O-.
5. **History:** donors, donations and issued blood, each with a button to export to CSV.

## Project layout

```
src/
  application/Launcher.java   start here
  db/Database.java            database connection, settings, blood-group helpers
  Login/  Panel/  Donor/  bloodUnitCollection/  issueBlood/  UnitsAvailable/
  allhistory/  history/  donationHistory/  issuedHistory/  MeetDeveloper/
blood_donor_database.sql      creates the database
db.properties                 your MySQL password and app login
pom.xml                       Maven build (JavaFX + MySQL driver)
```

