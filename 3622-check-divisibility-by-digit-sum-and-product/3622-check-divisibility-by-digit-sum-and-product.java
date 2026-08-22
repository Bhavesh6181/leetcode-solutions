class Solution {
    public boolean checkDivisibility(int n) {
        int total=0;
        int sum=0;
        int product=1;
        int temp=n;

        while(temp>0){
            int rem=temp%10;
            sum+=rem;
            product*=rem;
            temp/=10;
        }

        total=sum+product;

        return n % total == 0;

    }
}