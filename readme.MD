#ProcessTracker

##An android ui library to visualize a transactional process in steps.
### When the  user completes a transaction like say ordering some stuff in your ecommerce app you can the process tracker so that user can easily track the order like order confirmed, on the way or delivered.

##Usage
###Creating a process tracker in activity file
```
    ProcessTracker processTracker = new ProcessTracker(this);

```
###Creating a TrackProcess for individual process.
```
    TrackProcess orderConfirmedProcess = new TrackProcess(BitmapFactory.decodeResource(getResources(),R.drawable.order),"Order Confirmed");
```
###Adding the TrackProcess in ProcessTracker
```
    processTracker.addProcess(orderConfirmedProcess)
```
###Showing the TrackProcess
```
    processTracker.show()
```
