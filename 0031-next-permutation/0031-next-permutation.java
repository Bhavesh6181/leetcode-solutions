class Solution {

    public void nextPermutation(int[] nums) {

        int pivot = -1;

        // Find Pivot
        for (int i = nums.length - 2; i >= 0; i--) {

            if (nums[i] < nums[i + 1]) {

                pivot = i;
                break;
            }
        }

        // Largest permutation
        if (pivot == -1) {

            reverse(nums, 0, nums.length - 1);
            return;
        }

        // Find smallest greater element
        for (int i = nums.length - 1; i > pivot; i--) {

            if (nums[i] > nums[pivot]) {

                swap(nums, i, pivot);
                break;
            }
        }

        // Reverse suffix
        reverse(nums, pivot + 1, nums.length - 1);
    }

    public void reverse(int[] nums, int left, int right) {

        while (left < right) {

            swap(nums, left, right);

            left++;
            right--;
        }
    }

    public void swap(int[] nums, int left, int right) {

        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}