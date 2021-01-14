import sun.rmi.runtime.NewThreadAction;

import javax.xml.bind.annotation.XmlID;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Sort {
/*
 * 排序算法包括：
 * 冒泡排序 bubbleSort  1
 * 插入排序 insertionSort   1 学了数据结构再写一版
 * 选择排序 selectionSort   1
 * 归并排序 mergeSort   1
 * 桶排序 bucketSort   暂时不写，等学到后边再写。
 * 计数排序 countingSort    暂时不写，等学到后边再写
 * 基数排序 radixSort 暂时不写，等学到后边再写
 * 堆排序 heapSort
 * 锦标赛排序 tournamentSort
 * 快速排序 quickSort
 * 希尔排序 shellSort
 */
    public static  void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数组的规模：");
        int num = scanner.nextInt();
        int[] arr = new int[num];
        initArr(arr);

//        int[] arr1 = new int[num];
//        copyArr(arr1,arr);
//        int[] arr2 = new int[num];
//        copyArr(arr2,arr);
        String str1 = Arrays.toString(arr);
        System.out.println("arr = " + str1);
        insertionSort(arr);
        str1 = Arrays.toString(arr);
        System.out.println("arr = " + str1);
//        mergeSort(arr);
//        bubbleSort1(arr);
//        selectionSort(arr);
//        String str2 = Arrays.toString(arr);
//        System.out.println("arr = " + str2);
//        bubbleSort2(arr1);
//        str2 = Arrays.toString(arr);
//        System.out.println("arr1 = " + str2);
//        bubbleSort3(arr2);
//        str2 = Arrays.toString(arr);
//        System.out.println("arr1 = " + str2);
    }

    //拷贝数组：把arr的数据拷贝到arr1中去
    public static void copyArr(int[] arr, int[] arr1) {
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = arr[i];
        }
    }

    //初始化数组
    public static void  initArr(int[] arr) {
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(50)+1;
        }
    }
    //初始化数组
    public static void  initArr1(int[] arr) {
        Random random = new Random();
        for (int i = 1; i < arr.length; i++) {
            arr[i] = random.nextInt(50)+1;
        }
    }

    //交换数组中的两个元素
    public static void swapArr(int[] arr, int i, int j){
//        int tmp = 0;
//        tmp = arr[j];
//        arr[j] = arr[i];
//        arr[i] = tmp;
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    //冒泡排序1：通用版本，最基础的，效率最低
    public  static void bubbleSort1(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length- i - 1; j++) {
                if (arr[j] > arr[j+1]){
                    swapArr(arr,j,j+1);
                }
            }
        }
    }

    //冒泡排序2：若数列有序则提前退出外层循环
    public  static void bubbleSort2(int[] arr){
        boolean flag = true;
        for (int i = 0; i < arr.length; i++) {
            flag = true;//每次都假定序列有序
            for (int j = 0; j < arr.length- i - 1; j++) {
                if (arr[j] > arr[j+1]){
                    swapArr(arr,j,j+1);
                    flag = false; //发生交换，数组一定无序
                }
            }
            if(flag == true){
                break;
            }
        }
    }

    //冒泡排序3：
    public  static void bubbleSort3(int[] arr){
        int high = arr.length - 1;//表示左侧无序序列的长度
        int tmp = 0;
        while (high > 0){
            for (int i = 0; i < high; i++) {
                tmp = 0;
                if(arr[i] > arr[i+1]){
                    swapArr(arr,i,i+1);
                    tmp = i;
                }
            }
            high = tmp;
        }
    }


    //插入排序 insertionSort
    public  static  void insertionSort(int[] arr){
        int[] arr1 = new int[arr.length+1];
        for (int i = 1; i < arr1.length; i++) {
            arr1[i] = arr[i-1];
        }
        int len = 0;
        arr1[len] = arr1[len+1];
        len++;
        int p = 0;
        while(len < arr1.length - 1){
            p = 0;
            for (int i = 0; i < len; i++) {
                if (arr1[len+1] >= arr1[i]) {
                    p = i+1;
                }else{
                    break;
                }
            }

            for (int i = len; i > p; i--) {
                arr1[i] = arr1[i-1];
            }
            arr1[p] = arr1[len+1] ;
            len++;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr1[i];
        }
    }

    //归并排序
    /*
     * 思路：将序列分成两个子序列，分别将两个子序列排序，分别合并这两个排好的序列
     * 将两个有序的子序列合并为1个有序的子序列的过程称为二路归并
     */
    public static void mergeSort(int[] arr){
        int []tmp = new int[arr.length];
        mergeSort(arr,0,arr.length-1,tmp);
    }

    private static  void mergeSort(int[] arr,int low,int high,int[] tmp){
        if (low < high){
            int min = (high + low) / 2;
            mergeSort(arr,low,min,tmp);
            mergeSort(arr,min+1,high,tmp);
            merge(arr,low,min,high,tmp);
        }
    }

    private static void merge(int[] arr, int low, int min, int high, int[] tmp) {
        int i = low;
        int j = min + 1;
        int t = 0;
        while(i <= min && j<= high) {
            if (arr[i] <= arr[j]) {
                tmp[t++] = arr[i++];
            } else{
                tmp[t++] = arr[j++];
            }
        }
        while(i<=min){//将左边剩余元素填充进temp中
            tmp[t++] = arr[i++];
        }
        while(j<=high){//将右序列剩余元素填充进temp中
            tmp[t++] = arr[j++];
        }

        t = 0;
        while (low <= high){
            arr[low++] = tmp[t++];
        }
    }


    //选择排序 selectionSort
    public static void selectionSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int max = arr[0];
            int j = 0;
            int a = 0;
            for (j = 0; j < arr.length-i; j++) {
                if (arr[j] >= max) {
                    max = arr[j];
                    a = j;
                }
            }
            int tmp = arr[j-1];
            arr[j-1] = max;
            arr[a] = tmp;
        }
    }
}
