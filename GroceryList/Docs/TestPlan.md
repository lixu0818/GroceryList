# Test Plan


**Author**: Team 02

This product, named GroceryListManager, is an Android app for managing grocery lists that has some user-specified features.

## 1 Testing Strategy

### 1.1 Overall strategy

We will use the following types of testing to make sure that our final product performs as expect and meets all the requirements of our clients:

- **Unit Testing:** Unit testing will be done for all components with code coverage and using mock objects for depencies.
- **System Testing:** System tests will test the complete system end to end.
- **User Acceptance Testing:** We will also write BDD style UAT tests to ensure our product meets the quality for our end-users and customers.

### 1.2 Test Selection

1. **Unit Tests (white-box)** will be written for all non-UI functionalities. These tests will be developed simultaneously with the main application development, and whenever new functions are added in later phases. Unit tests should be performed regularly throughout the development phase.

2. **System Tests (black-box)** will be developed at the beginning of application development. These tests should be performed at each release point (alpha/beta) to ensure that all of the user-defined requirements are met. These tests may change as we move forward with development cycles.  The system tests will be driven from the View/Presenter components.

3. In the end, we will use **User Acceptance Testing** to ensure that the product performs as expected. Once again, these tests may change as we move forward with development cycles. The UAT testing will be driven from the UI layer and automated as if a user is driving the application.


### 1.3 Adequacy Criterion

- Unit testing will be complete when code coverage is higher than 80% at least.

- System and Acceptance testing will be complete when all test cases described in the below section pass.

- The testing phase will be considered complete when all the above test cases defined are passed and all the bugs are addressed. 


### 1.4 Bug Tracking

We will open, close, merge and track issues using Github: https://github.gatech.edu/issues

### 1.5 Technology

- JUnit will be used for unit testing. 
- System testing and User Acceptance Testing will be performed with [selendroid](http://selendroid.io/) or [robotium](https://github.com/RobotiumTech/robotium). 
- Other useful frameworks such as [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/) and [Calabash](http://calaba.sh/) will also be considered if necessary.

**For deliverable4, all the black box testings are performed manually, we are looking into adding automated testings for future release**

## 2 Test Cases

### 2.1 Unit Testing

For unit testing we will be testing all the operations (success and failure) as documented in the class diagram and aim for a high code coverage as documented in the adequacy criterion. Listing of all the test cases (success an failure) for all the operations is in-feasible.

### 2.2 System Testing and Acceptance Testing

For system and acceptance testing we will test for the following test cases. The test cases below are tested manually and not rigorously. We believe that for rigorous testing and to record our tests in terms of executable tests (instead of the below table)
we need automated functional tests. We will complete more fine grained functional tests with an automated testing framework for the next iteration of the project.

The status of the test plan in this iteration is as follows:

Test Case  | Purpose |  Steps | Expected Results | Actual Results | Pass/Fail. | Comments |
 ------------ | :--------------: | :-----------: | :-----------: |:-----------: |:-----------:|-----:|
**View List-1** | Displays the grocery lists from the database.  | User opens the app and sees a list of his grocery lists. | All grocery lists for the user are displayed. |  A list of groceries are displayed | Pass | Working as intended
**View List-2** | Test how app displays grocery lists not fit in one page. | User opens the app and add 20 new grocery lists. | All grocery lists are displayed and can be scrolled down. |  All grocery lists are displayed and can be scrolled down. | Pass | Working as intended
**Add List-1**  | Adds the grocery list to the database.      | User will press the 'Add' button, enter a name for the list and press 'Save'.  | The grocery list is added to the database. A new record in GrocerySet table is created.  | New list can be added to the grocery list table in the database. | Pass | Working as intended
**Add List-2**  | Test how app responds to addition of a new list with no name.  | User will press the 'Add' button, do not type anything and press 'Save'.  | A message shows up to instruct user for correct input. | A error message pops up and ask the user to provide a list name. | Pass | Working as intended.
**Add List -3**  | Test how app responds to addition of a grocery list with a duplicate name.      | User will press the 'Add' button, enter a name for the list that already exists and press 'Save'.  | A new list with the duplicated name is allowed. | A New list with the duplicated name is added to the grocery list table in the database. | Pass | Working as intended
**Delete List**  | Delete a grocery list from the database.|User will click on the checkbox to the left of the grocery list and press 'remove' button. | The grocery list is deleted from the database for the corresponding row in the GrocerySet table. | The grocery list is removed from the view and the DB | Pass | Working as intended
**Delete List-2**  | Delete multiple grocery lists from the database at the same time.|User will click on the checkboxs of the grocery lists and press 'remove' button. | The grocery lists are deleted from the database for the corresponding row in the GrocerySet table. | The grocery lists are removed from the view and the DB | Pass | Working as intended
**Delete List-3**  | Test how app responds to deletion without selecting a list first.| User click on area with no grocery list or directly click on the 'Remove Grocery' button. | Nothing happens. | Nothing happens. | Pass | Working as intended
**Rename List**    | User can edit the grocery list name in the input text box while viewing all the grocery lists.     | User will click on the checkboxs of the grocery lists and press 'rename' button. On the next page, type in a new name and press 'rename' | Then name of the grocery list is updated in the database for the corresponding row in the GrocerySet table. | Pass | | Working as intended
**Rename List-2**  | Test how app responds to rename without selecting a list first.| User click on area with no grocery list or directly click on the 'Rename Grocery' button. | Nothing happens. | Nothing happens. | Pass | Working as intended
**Rename List-3**  | Test how app responds to renaming of a list with no name.  |User will click on the checkboxs of the grocery lists and press 'rename' button. On the next page, press 'rename' without providing a name | A message shows up to instruct user for correct input. | A error message pops up and ask the user to provide a list name. | Pass | Working as intended.
**Add Item to List** | Add an item to a grocery list and save to the database. | User will press the 'Add' button underneath the grocery list to add an item to the grocery list, go to the next UI to add the item name( and type) and quantity( and type) and press 'Save'. | The grocery item is added to the grocery list for the user in the database, which means, a new record is added to GroceryItem table  in the database. | Grocery item is added to the list, and dispaly its name, quantity and unit | Pass | Working as intended.
**Add Item to List-2** | Test how app responds to adding an item with decimal for quantity | When adding an item to list, user type 2.5 for qunatity. | The grocery item is added to the grocery list. | Grocery item is added to the list, and dispaly its name, quantity and unit | Pass | Working as intended.
**Add Item to List-3** | Test how app responds to adding an item with decimal for quantity | When adding an item to list, user type 2.5 for qunatity. | The grocery item is added to the grocery list. | Grocery item is added to the list, and dispaly its name, quantity and unit | Pass | Working as intended.
**Remove Item(s) from List**  | Remove item(s) from the grocery list and delete from the database.   | User will click a list of checkboxes for the items in the grocery list and press 'Remove' for that grocery list.    | The grocery item(s)  are removed from the grocery list in the database for the user, which means, the corresponding row from the GroceryItem table is deleted from the database. | The items are removed from the view and the DB | Pass | Working as intended
**Remove Item(s) from List-2**  | Test how app responds to Remove Item without first selecting an item.| User click on area with no item or directly click on the 'Remove' button. | Nothing happens. | Nothing happens. | Pass | Working as intended
**Mark item(s) as checked off** | Checkoff or strikeoff items in the grocery list and mark the items as checked off in the datrabase. | User will click a list of checkboxes for the items in the grocery list and press 'Check/Un-check' for that grocery list. | The grocery item(s)  are checkoffed from the grocery list in the database for the user, which means the checked_off column  for the GroceryItem table is set to true. | The items are removed from the view and the DB  | Pass | Working as intended
**Uncheck item(s)** | Uncheck previously checked-off items in the grocery list and mark the items as unchecked in the datrabase. | User will click a list of checkboxes for checked-off items in the grocery list and press 'Check/Un-check' for that grocery list. | The grocery item(s)  are unchecked from the grocery list in the database for the user, which means the checked_off column  for the GroceryItem table is set to back to false. | | Pass | Working as intended  | 
**Search items by Name** | Search for items by a name.  | User will enter the name of the item into the text box. | If the item exist in the database, then the item name shows up in autocompletion. | same as expected | Pass | Working as intended
**Add Item to database**  | Add an item to the database.  | User will enter an item name and an item type and quantity(and type) and press 'Save'. | The item and the item type is created in the database. | The item can be added. | Pass | Working as intended

We performed intensive UAT testing manually, and used Git Issues to track all bugs of our app. In the limited time frame, we were able to close 19/25 issues (https://github.gatech.edu/gt-omscs-se-2016fall/6300Fall16Team02/labels/bug) with the remaining 6 isssues marked as 'wont-fix' and improved the overall performance of our app. We can continue to add more features and functionalities to this app even after submitting this group project. So that it can be better in future releases.

We have performed automated UAT testing using Espresso. But the tests are not pushed into master as we completed this late and the automated tests require different version of dependencies on the test jars. We do not want to risk the project into compilation/run issues due to this. Also, the automated tests have an issue with the DB where it does not
tear the testcases down cleanly. The testcases are on uat-tests branch (https://github.gatech.edu/gt-omscs-se-2016fall/6300Fall16Team02/tree/uat-tests). 

A main criterion of failure testing would be that the application doesn't **crash or exit** when an exception or error is thrown - Passes. The application does not crash during usage or testing.

