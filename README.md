# DeadLock
seek to solve deadlock problem , Therefore, i apply the deadlock avoidance algorithms by applying the Banker's algorithm

Banker algorithm  __grant resource only if__ process __leaves__ system in __safe state__ otherwise __request__ is __denied__ if it leaves the system in un __Safe State__ 

## How it Works :
  1-we keep track of the resources using these data structure:
    i-int [] available; //the available amount of each resource
    ii-int [][] maximum; //the maximum demand of each process
    iii-int [][] allocation; //the amount currently allocated to each process
    iv-int [][] need; //the remaining needs of each process


## See also :
 * ### [Virtual-File-System-with-Protection](https://github.com/MarwanaMostafa/Virtual-File-System-with-Protection)

 * ### [Disk-Scheduling](https://github.com/MarwanaMostafa/Disk-Scheduling)
