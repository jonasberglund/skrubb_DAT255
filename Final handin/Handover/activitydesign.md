#The Activity Design
In our application we use a slide menu to navigate to the different activities and we have chosen to let all activities extend an Activity called BaseActivity.
The BaseActivity class keeps information about which the current activity (view)
is and the menu. These variables in BaseActivity are static,
which let all subclasses use them (it is always the same menu). All activites also use the same
layout (activity_main.xml), but they fill it up with different content. This is done because of the
menu, so it do not have to be in all activities and xml files.
