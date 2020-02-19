hatch-androidcodingchallengejava

Project Description:

Evaluation Requirements:

We want to have a working project that we can open and run in Android Studio to test and evaluate. Please send us a zip file of your completed project.
We will be evaluating your code, so make sure you structure your modular components logically and explain interesting bits of code with comments.
While this code is meant to be evaluated, we obviously do not expect production quality code, comments, etc. Focus on the important areas.
We value your time. This should take around 2 hours, but please do not spend more than 3 - if it’s taking longer, just send us what you have.
Code should be written using Kotlin or modern Java and modern layout techniques (such as ConstraintLayouts, RecyclerViews, etc).
Do not use any 3rd party code, only official Android frameworks and your own original code. (You can search for information online, but make sure you fully understand all of the code you use - do not just copy and paste stuff).
We may discuss your project in a follow-up session.
Be ready to explain how your code works, why you made various (technical) design decisions, and how various areas of the code or functionality can be improved or extended.

Functional Requirements:

You should be able to list all of the user’s contacts, even if there are thousands.
The list should display the favorite contacts on the top of the list.
The user shouldn’t have to wait a long time while all of the contacts load, since they won’t all be on-screen ;).
The user should have some information or indication in the UI while particular data is loading or if it is not available.
Please build a RecyclerView listing - do not use the ListView framework.
Your custom contact cell should show the contact’s name and the readable date-time format from when it was last modified. It should also have a button that should allow the user to send a message to the contact (to the phone number or email, no need to support both - for this you can present a native modal message).
Make sure these components lay out cleanly together (show us you know how to use ConstraintLayout in your custom cell).
Scope limitation, do not spend time on:
- Tablet layout, just focus on phone.
- Handling orientation changes.
- Localization/globalization.
- Handling data updates/changes (new contact created, or contacts edited while the app is running or between runs).
- We may discuss these topics in follow-up conversations, but do not spend time on them in this project.

References:

https://developer.android.com/guide/topics/providers/contacts-provider

https://developer.android.com/reference/android/provider/ContactsContract.Contacts.html


Installation Steps:

Please make sure that Android Studio is up to date with latest SDK. The minimum SDK supported is API23, and compiler SDK and target SDK are all set to API29.

I've setup a public repository for the code where the latest code changes are in master here: https://github.com/ErnestHolloway8482/underarmour-coding-exercise-holloway.git

Please clone the repository using your favorite GitHub Link or simply download the project as a zipfile. You should not need any credentials to clone.

Once you successfully clone the project please make go to Android Studio->Settings->Preferences->Build, Execution, Deployment within the Android Studio IDE.

Choose a device with actual contacts on it and install the debug build variant of the app onto it. I didn't make a production key so this is the only one that will work.

The app will automatically run to display the lists of contacts with the favorite contacts being listed first. All contacts, per the
specification will be listed in order of the the most recent modified contact first.

As you scroll additional contacts will be loaded. Note depending on the speed of your device you may not see a loading spinner. 

Important Notes:


-The plain old AsyncTask is being utilized here instead of RxJava since the requirement is to avoid use of 3rd party resources.
The newer practice today is to use RxJava or utilize Kotlin Coroutines instead of Async Tasks as it has better mechanisms in place
to avoid run-away threads, memory leaks, and gives you full control to start/stop background threads compared to Async Tasks.

-I made sure to use the CursorLoader along with the appropriate callbacks for displaying the data. It provides me with the content loader
which is needed to access the contacts database and provides a managed background thread, and notification of data set changes with the least amount of work.

-In the interest of time, I avoided using Android's MVVM framework along with the powerful data binding tools that I
typically use in my greenfield production level projects. I've included additional coding samples as part of my submission so that you can
see different type of project work that I've done in the past.

-In the interest of time I also avoided doing package arrangement of the Java classes for now.

-For simplicity, the contacts listing does not avoid displaying duplicate entries. So you may see the same contact listed more than once as long as it has a phone number. This is something that can be updated later if given more time.

-When the user sends a message it is assumed to be a text message only, per the dialog prompt instructions presented to the user.
Also, if the contact does not have a number then the app will not let the user send a text. 

-In the interest of time, the CursorLoader query pulls all the contacts with at least one phone number it is not account type specific.
Since I am using a CursorLoader and the appropriate LoaderManager.LoaderCallback, if you add a new contact with at least one phone number to the contact database it will automatically reflect
within the UI. I got this for free since it was simpler to use the CursorLoader anyway to pull the user's contacts from the internal database.

-When the content is displayed the contacts are sorted such that the favorites are displayed first. The list is displayed in so that the most recently modified contact
is displayed first.

-The time stamp display for the last modified date is MM-dd-YYYY HH:mm:ss

-Only critical items of the code are commented for brevity.

-The screen orientation is permanently locked to portrait mode.

-To support basic error handling, If the user denies the contacts permissions during the check, they'll see a Toast message explaining why they should accept it and the screen 
and an empty state text will be displayed in the center of the screen.








