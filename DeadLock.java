import java.util.Scanner;

public class FinalAss1 {
    public static int NP = 5; // Number of processes
    public static int NR = 3; // Number of resources
    public static int FaildSystem = 0; // When alllllllllllll resources< need all process

    public static int need[][] = new int[NP][NR];
    public static int[][] max = new int[NP][NR];
    public static int[][] allocation = new int[NP][NR];
    public static int[] available = new int[NR];

    public static int tempNeed[][] = new int[NP][NR];
    public static int[][] tempMax = new int[NP][NR];
    public static int[][] tempAllocation = new int[NP][NR];
    public static int[] tempAvailable = new int[NR];

    public static boolean[] FinishProcess = new boolean[NP];
    public static String safeSequence = "";
    public static Scanner input = new Scanner(System.in);

    /********************************** PRINT**************************** */
    public static void PrintAllocation(int numProcess) {
        for (int i = 0; i < NR; i++)
            System.out.print(allocation[numProcess][i]);
    }

    public static void PrintMax(int numProcess) {
        System.out.print("     ");
        for (int i = 0; i < NR; i++)
            System.out.print(max[numProcess][i]);
    }

    public static void PrintNeed(int numProcess) {
        System.out.print("     ");
        for (int i = 0; i < NR; i++)
            System.out.print(need[numProcess][i]);
    }

    public static void Print() {
        System.out.println("       Allocation      Max      Need ");
        for (int i = 0; i < NP; i++) {
            System.out.print("P" + i + "       ");
            PrintAllocation(i);
            System.out.print("      ");
            PrintMax(i);
            System.out.print(" ");
            PrintNeed(i);
            System.out.println();
        }

    }

    public static void PrintAvailable(int numProcess) {
        System.out.print("AVAILABLE RESOURCE NOW After Excute  Process " + numProcess + ":");
        for (int i = 0; i < NR; i++)
            System.out.print(available[i] + "  ");
        System.out.println(" \n");
    }

    /************************ TEST CODE AND CALCULATE NEED ********************/
    public static void initializeValues() {
        
        System.out.println("Enter number of Process");
        NP = input.nextInt();
        System.out.println("Enter number of Resources");
        NR = input.nextInt();
        for (int i = 0; i < NP; i++) {
        System.out.println("Enter Allocated Resource for P" + i);
        for (int j = 0; j < NR; j++) {
        allocation[i][j] = input.nextInt();
        tempAllocation[i][j] = allocation[i][j];
        }
        }
        for (int i = 0; i < NP; i++) {
        
        System.out.println("Enter Max Resource for P" + i);
        for (int j = 0; j < NR; j++) {
        max[i][j] = input.nextInt();
        tempMax[i][j] = max[i][j];
        }
        }
        
       // FinishProcess = new boolean[] { false, false, false, false, false };
       // allocation = new int[][] { { 0, 1, 0 }, { 2, 0, 0 }, { 3, 0, 2 }, { 2, 1, 1 }, { 0, 0, 2 } };
       // tempAllocation = new int[][] { { 0, 1, 0 }, { 2, 0, 0 }, { 3, 0, 2 }, { 2, 1, 1 }, { 0, 0, 2 } };
       // max = new int[][] { { 7, 5, 3 }, { 3, 2, 2 }, { 9, 0, 2 }, { 2, 2, 2 }, { 4, 3, 3 } };
       // tempMax = new int[][] { { 7, 5, 3 }, { 3, 2, 2 }, { 9, 0, 2 }, { 2, 2, 2 }, { 4, 3, 3 } };

         System.out.println("Enter Available Resource in Bank");
         for (int j = 0; j < NR; j++) {
         available[j] = input.nextInt();
         tempAvailable[j] = available[j];
         }
        //available = new int[] { 3, 3, 2 };// 230 this case occur deadlock and sovle it in end
        //tempAvailable = new int[] { 3, 3, 2 };
    }

    public static void calculateNeed() {
        for (int i = 0; i < NP; i++)
            for (int j = 0; j < NR; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
                tempNeed[i][j] = need[i][j];
            }
    }

    public static void testCode() {
        initializeValues();
        calculateNeed();
        System.out.println("\n        DATA OF PROCESSES \n");

        Print();

        System.out.println("\n");

        if (checkDeadLock())
            System.out.println("DEADLOCK OCCUR FOR THIS PROCESSES");
        else {
            System.out.println("NO DEADLOCK and Sequence of Processes is : ");
            System.out.print(safeSequence);
        }

    }

    /********************************** BANKER ALOG *******************/
    public static boolean checkDeadLock() {// return true: exist DEADLOCK, FALSE:not exist DEADLOCK
        // boolean visited[] = new boolean[NP];
        // for (int i = 0; i < NP; i++) {
        // visited[i] = false;
        // }
        int count = 0;
        while (count < NP) {
            boolean flag = false;
            for (int i = 0; i < NP; i++) {
                if (FinishProcess[i] == false) {
                    int j;
                    for (j = 0; j < NR; j++) {
                        // why hence > no >= because if process need 111 and available 111 take this
                        // condition will be false
                        if (need[i][j] > available[j]) {// The bank decisionsto either deny or approve the requests must
                            // be shown through the output screen
                            System.out.println("bank Deny Request For P " + i + " Because Request >available\n");
                            break;
                        }
                    }
                    if (j == NR) {
                        FinishProcess[i] = true;
                        // visited[i] = false;
                        safeSequence += "P" + i + "  ";
                        flag = true;// because if there isn't any process excute so not enter while loop agian
                                    // because condition while depend on count

                        addRelaseResources(i);// add each instance in each position
                        FinishProcess(i);
                        PrintAvailable(i);
                        Print();
                        count++;
                    }
                }
            }
            if (flag == false)// all need process < available Resources
                break;
        }
        boolean IsDeadLock = false;
        for (int i = 0; i < NP; i++)// check if process not execute or no
            if (!FinishProcess[i])
                IsDeadLock = true;
        if (IsDeadLock) {
            // this msg to clarify that exist function Rcovery
            System.out.println("NOTE DEADLOCK IS OCCUR AND IF YOU NEED SOVLE ENTER 1 else ENTER 2");
            int choice = input.nextInt();
            if (choice == 1)
                if (FaildSystem >= NP)// increase in reocvery prvent enter infinty loop
                    return true;
                else
                    return recovery();
            return true;// remember this return not important but if user don't need solve DeadLock
        } else
            return false;
    }

    /******************************* RECOVERY ALGO *******************/
    public static boolean recovery() {
        int bigAllocated = 0;
        int temp1 = 0;
        int temp2 = 0;
        for (int i = 1; i < NP; i++) {
            for (int j = 0; j < NR; j++) {
                temp1 += allocation[bigAllocated][j];
                temp2 += allocation[i][j];
            }
            if (temp1 < temp2)
                bigAllocated = i;
            temp1 = temp2 = 0;
        }

        for (int i = 0; i < NR; i++) {
            available[i] += allocation[bigAllocated][i];
            need[bigAllocated][i] += allocation[bigAllocated][i];
            allocation[bigAllocated][i] -= allocation[bigAllocated][i];
        }

        System.out.print("Available Resource After Realse resources P" + bigAllocated + ":");
        for (int i = 0; i < NR; i++)
            System.out.print(available[i] + " ");

        System.out.println();
        Print();
        FaildSystem++;
        return checkDeadLock();
    }

    /******************************** RL,CHECK RELASE RESOURCES *******************/
    public static void RL() {
        System.out.println("IF INFORAMTION OF PROCESSES CHENGE AND YOU NEED INFORMATION AS ENTERD FIRST ENTER 1 ");
        int choice = input.nextInt();
        if (choice == 1)
            restartInfoProcesses();

        System.out.println("Enter Number Process ");
        int numOfProcess = input.nextInt();
        int tempRelase[] = new int[NR];

        System.out.println("Enter Release Resource");
        for (int i = 0; i < NR; i++)
            tempRelase[i] = input.nextInt();

        // release resources are less than or equal(to this condition we not put equal
        // in funcion check relase) to allocated resources
        if (checkRelaseResource(numOfProcess, tempRelase)) {
            for (int i = 0; i < NR; i++) {
                allocation[numOfProcess][i] -= tempRelase[i];
                available[i] += tempRelase[i];
                need[numOfProcess][i] += tempRelase[i];
            }
            System.out.println("Release Resource Done And Information Of Processes after Relase is ");
            Print();
            System.out.println("Avaialbe Resource After Relase : ");

            for (int i = 0; i < NR; i++)
                System.out.println(available[i] + "  ");
        } else
            System.out.println("INVALID RELASE");
    }

    public static boolean checkRelaseResource(int numOfProcess, int[] Release) {
        for (int j = 0; j < NR; j++)
            if (allocation[numOfProcess][j] < Release[j])
                return false;
        return true;
    }

    /******************************** SOME FUNTIONALITY *******************/
    public static void restartInfoProcesses() {
        for (int i = 0; i < NP; i++) {
            for (int j = 0; j < NR; j++) {
                allocation[i][j] = tempAllocation[i][j];
                max[i][j] = tempMax[i][j];
                need[i][j] = tempNeed[i][j];
            }
            FinishProcess[i] = false;
        }
        for (int j = 0; j < NR; j++) {
            available[j] = tempAvailable[j];
        }
        safeSequence = " ";
        FaildSystem = 0;
    }

    public static void addRelaseResources(int NumProcess) {
        for (int j = 0; j < available.length; j++)
            available[j] += allocation[NumProcess][j];
    }

    public static void FinishProcess(int numOfProcess) {
        for (int i = 0; i < NR; i++) {
            need[numOfProcess][i] = 0;
            allocation[numOfProcess][i] = 0;
            max[numOfProcess][i] = 0;
        }
    }

    /******************************** RQ *******************/
    public static void RQ() {// 1 1 1 1 no dead lock , 0 0 1 0 exist deadlock
        System.out.println("IF INFORAMTION OF PROCESSES CHENGE AND YOU NEED INFORMATION AS ENTERD FIRST ENTER 1 ");
        int choice = input.nextInt();
        if (choice == 1)
            restartInfoProcesses();

        System.out.println("Enter Number Process ");
        int numOfProcess = input.nextInt();
        int tempReq[] = new int[NR];

        System.out.println("Enter Request  Resource");
        for (int i = 0; i < NR; i++)
            tempReq[i] = input.nextInt();

        // So add this request to the needed resources for the given process
        for (int i = 0; i < NR; i++) {
            need[numOfProcess][i] += tempReq[i];
            max[numOfProcess][i] += tempReq[i];
                }
        if (checkDeadLock()) {// return True:System is UnSafe ,Return false: System is safe
            System.out.println("System UnSafe Because Process" + numOfProcess + " So Request Denied");
            for (int i = 0; i < NR; i++)// note hence we take request resource because return system to defualt
                                        // state(lecture Ex)
            {
                need[numOfProcess][i] -= tempReq[i];
                max[numOfProcess][i] -= tempReq[i];
            }
        } else
            System.out.println("Request is approve and System In Safe");
    }

    /******************************** MENU *******************/
    public static void Menu() {
        int choice = 0;
        while (choice != 3) {
            System.out.println("\n1-RQ \n2-RL \n3-Quit");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    RQ();
                    break;
                case 2:
                    RL();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        testCode();
        // checkDeadLock();
        Menu();
    }
}
