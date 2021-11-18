# contacts
1.Download Android Studio
   https://developer.android.com/studio/index.html

Click “Download Android Studio”, accept the terms and conditions, and proceed with the download.
The download might take a while, as it is about 700 MB. Run the installer after the download is finished.
1.Install Android Studio
When running the installer, we recommend using the default values, as shown below. Once installed, Android Studio will run, and launch a setup wizard to download additional components.


1.Run Android Studio Setup Wizard
When you first start Android Studio, it will offer to import any previous settings. Since this is likely your first installation, there are no settings to import. Again, just accept the default option and continue.

Next, select the UI theme. Choose whichever one you prefer -- this is simply a matter of preference. On the next screen, select the option to install an Android Virtual Device. You will need an actual Android device for this course, but this virtual device can be useful.

Accept the recommended RAM size for the emulator, and click “Next” to see a summary of the components to be downloaded. The additional components total about 1.8 GB in size.

When the download begins, the progress bar might appear to freeze, but do not worry -- the download is progressing. Once installation is complete, click “Finish”.

1.Begin Developing
The Create New Project appears. The first option is to select the type of activity. Choose Empty, which is the default and click Nex t.
 

Configure your project is the next screen. It will ask for the name of the Application, Package Name, Project path, language, and API Level. Keep the defaults and click on Finish


Wait for it to finish. Once everything is downloaded and installed, the new project is created and you are taken to the Android workspace.
Create New Virtual Device
If you are starting the AVD Manager for the first time,  Else you will see the list of AVDs created.you will see the following screen. 
 	 
In the left-hand panel displays a list of the Category of the device.  It includes TV, Phone, Wear & Tablet. Select the category.
The middle pane displays the list of devices available. Select one based on the requirement of your app. After this click on the Next button.
Note that phones with larger resolution Choose the pixels resolution according to your requirements as it will take huge RAM in large pixels resolution device. If your computer has low RAM, then prefer to choose less resolution device. Click Next to continue.

Choose the system image based on the API level targeted by your App. The app won’t run if you choose lower API than the one target by the App, Select the image and click on Next to continue.
Here you can name your AVD, change start-up orientation and few other hardware properties. Click on Show Advanced Settings to show more settings.
Click on Finish to create the AVD.
 
Under the action column, click on the   icon to run the AVD. The Android Emulator uses the AVD to mimic the device.  You can then use the control panel to manage the device. The Extend control button at the bottom gives you more options
Contacts Application documentation
In this application, we are going to see how to load Contacts Information on your own Android Application. You can customize the display of the Contact List (here we use just RecyclerView). How to add another new contact through dailog box, delete a single or multiple contacts using checkbox, checked items to uncheck, how to pass the list of items to another activity using parcelable, and how to get back the list of items to foreground Activity, using swipe refresh the list shows as before.

RecyclerView:
RecyclerView makes it easy to efficiently display large sets of data. You supply the data and define how each item looks, and the RecyclerView library dynamically creates the elements when they're needed. As the name implies, RecyclerView recycles those individual elements.

Dependency:
implementation 'androidx.recyclerview:recyclerview:1.2.1'

Example:

  LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    adapter=new RecyclerAdapter(inflater, selectUsers,this,false);
    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    recyclerView.setAdapter(adapter);

Creating Menu:
To create a menu, you need a menu folder, so create one inside the “res” folder by selecting it and choosing “File”, “New”, then “Folder” and entering “menu” as the name.

Menus:
The options menu is the primary collection of menu items for an activity. It's where you should place actions that have a global impact on the app, such as "Search," "Compose email," and "Settings”.
Example:
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

<item 
      android:id="@+id/action_search"
        android:title="Search"
        android:icon="@drawable/ic_baseline_search_24"
        app:showAsAction="always|collapseActionView"
        app:actionViewClass="android.widget.SearchView"/>

    <item
        android:id="@+id/delete"
        android:title="delete"
        app:showAsAction="ifRoom"
        android:icon="@drawable/ic_baseline_delete_24">

    </item>

    <item
        android:id="@+id/close"
        android:title="close"
        app:showAsAction="ifRoom"
        android:icon="@drawable/ic_baseline_close_24">

    </item>


Operations:
⦁	Adding contact 
⦁	Delete
⦁	Search
⦁	Close


Add:
Is used to adding a new contact and notifies the list.
Example:
Contacts contacts = new Contacts();
contacts.setName(Name);
contacts.setPhone(PhoneNum);
selectUsers.add(contacts);
adapter.notifyDataSetChanged();


Delete:
 	Is used to delete single and multiple selected items, and removes the deleted items list and notifies the list.
Example:
for (int i = 0; i < selectUsers.size(); i++) {
 				   if (selectUsers.get(i).isChecked()) {
   				     Log.d("testingTAG", String.valueOf(i));
   				selectUsers.remove(i);
   				 	i--;
  				  }
			}
adapter.notifyDataSetChanged();


Search:
The filterList() method has to be overridden in the adapter class in which the filter condition is provided to search through a list. Below is an example of filterList() method to search a contact by name from a list of contacts.
Example:
private void filter(String newText) {
    List<Contacts> filteredList = new ArrayList<>();
    for (Contacts item : selectUsers) {
        if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
            filteredList.add(item);
    		    }
}


Close:
Is used to make the checked Items, Uncheck and notifies the list.
Example:
for (int i = 0; i < selectUsers.size(); i++) {

 		   selectUsers.get(i).setChecked(false);}

		adapter.notifyDataSetChanged();

Intent and Intent types:
An Intent is a messaging object you can use to request an action from another app component. Although intents facilitate communication between components.


1.Implicit Intent doesn’t specify the component. In such a case, intent provides information on available components provided by the system that is to be invoked. For example, you may write the following code to view the webpage.
2.Explicit Intent specifies the component. In such a case, intent provides the external class to be invoked.

Parcelable: Is used to pass the list of selected items to one activity to another activity using the share button at the toolbar.
Example:
Intent intent = new Intent(this, SelectedItems.class);
 Bundle b = new Bundle();
 b.putParcelableArrayList("checkBoxValue", (ArrayList<? extends Parcelable>) selectedContacts);
 intent.putExtras(b);
 startActivityForResult(intent, 1);
OnActivityResult: Is used to get back the list of items into foreground activity, after if we refresh the page it should show as same as before list.
Example:
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 1) {
        if(resultCode == RESULT_OK){
Bundle b = data.getExtras();
            final ArrayList<Contacts> newlist = b.getParcelableArrayList("result");
            Toast.makeText(this, "selected items "+newlist.size(), Toast.LENGTH_LONG).show();
 

        }  if (resultCode == RESULT_CANCELED) {

            Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
        }
    }

}


