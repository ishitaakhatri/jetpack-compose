This project implements a dynamic dashboard screen in Android using Jetpack Compose, where UI widgets are rendered entirely based on backend metadata.

WORKFLOW

The backend sends a list of widget instructions in the following format:
-Type => banner or list
-InstanceId => "string" {which can be movie,show,pokemon}

dashboard screen => this is where i have used lazycolumn and we render widgets dynamically using metadeta 
widget types => listwidget{state full}                  ,Bannerwidget{stateless} 
                  states-> error,loading,success           example:- pokemon,bike,car
                  example:- movies,shows

banner widget => 
    .Displays one or more banners
    .Each banner contains a title and subtitle
    .Rendered horizontally using LazyRow
    .Data is driven by instanceId


list widget => 
    .Displays items vertically
    .Each list widget:
      .Has its own ViewModel
      .Manages its state independently
      .Is scoped using instanceId


The project follows an MVVM-style architecture:

  DashboardViewModel:
    .Simulates backend metadata
    .Supplies widget configuration to the UI

  Widget-level ViewModels:
    .Only used for stateful widgets (ListWidget)
    .Created using instanceId as a key
    .Prevent shared state issues

please find the architectural diagram through these links:- 
https://drive.google.com/file/d/1yckI9GePjhw8EJStU1uk7GCeHOw1OgyV/view?usp=sharing
https://drive.google.com/file/d/1q7ySCW0BQMj5wmoI-vPx36qcn7RRuOCA/view?usp=sharing

thank you
