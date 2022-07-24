# DeadLock
seek to solve deadlock problem , Therefore, i apply the deadlock avoidance algorithms by applying the Banker's algorithm

Banker algorithm  __grant resource only if__ process __leaves__ system in __safe state__ otherwise __request__ is __denied__ if it leaves the system in un __Safe State__ 

## How To Run :
    1-Enter Number of Process
    
    2-Enter Number of Resources
    
    3-Enter Allocation resourcess for each process 
    
    4-Enter maximum demand(Max) resourcess for each process 
    
    5-Enter Avaliable Resources


## How it Works :
  1-we keep track of the resources using the following  data structure:
   
    i-  int [ ] available; //the available amount of each resource
   
    ii- int [ ][ ] maximum; //the maximum demand of each process
   
    iii-int [ ][ ] allocation; //the amount currently allocated to each process
   
    iv- int [ ][ ] need; //the remaining needs of each process
    
 2-Process can request(RQ) resource and add these resources to needed array for this process __for the given process and check again if the system is in a safe state .__ 
 
 3-if request let to __DeadLock__. we do check each process and do release for The process that has the __most number__ of rsources

 
 4- Process can release (__RL__)resource so check if release resources are less than or equal to allocated resources and if so, then subtract 
    this release resources from allocated resources for a given process.

 

## See also :
 * ### [Virtual-File-System-with-Protection](https://github.com/MarwanaMostafa/Virtual-File-System-with-Protection)

 * ### [Disk-Scheduling](https://github.com/MarwanaMostafa/Disk-Scheduling)
