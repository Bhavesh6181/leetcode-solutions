class Solution {
    private static final int[][] EXP = {
        {0,0,0,0}, {0,0,0,0}, {1,0,0,0}, {0,1,0,0}, {2,0,0,0},
        {0,0,1,0}, {1,1,0,0}, {0,0,0,1}, {3,0,0,0}, {0,2,0,0}
    };

    public String smallestNumber(String num, long t) {
        long tt = t;
        int a=0,b=0,c=0,d=0;
        while(tt%2==0){tt/=2;a++;}
        while(tt%3==0){tt/=3;b++;}
        while(tt%5==0){tt/=5;c++;}
        while(tt%7==0){tt/=7;d++;}
        if(tt!=1) return "-1";

        int n = num.length();
        int[] dig = new int[n];
        for(int i=0;i<n;i++) dig[i]=num.charAt(i)-'0';

        int[] pe2=new int[n+1], pe3=new int[n+1], pe5=new int[n+1], pe7=new int[n+1];
        int firstZero = n;
        boolean foundZero=false;
        for(int i=0;i<n;i++){
            pe2[i+1]=pe2[i]; pe3[i+1]=pe3[i]; pe5[i+1]=pe5[i]; pe7[i+1]=pe7[i];
            int dgt = dig[i];
            if(dgt==0){
                if(!foundZero){ firstZero=i; foundZero=true; }
            } else {
                int[] ex = EXP[dgt];
                pe2[i+1]+=ex[0]; pe3[i+1]+=ex[1]; pe5[i+1]+=ex[2]; pe7[i+1]+=ex[3];
            }
        }

        if(!foundZero){
            if(pe2[n]>=a && pe3[n]>=b && pe5[n]>=c && pe7[n]>=d){
                return num;
            }
        }

        int maxI = Math.min(n-1, firstZero);
        for(int i=maxI;i>=0;i--){
            int re2=Math.max(0,a-pe2[i]);
            int re3=Math.max(0,b-pe3[i]);
            int re5=Math.max(0,c-pe5[i]);
            int re7=Math.max(0,d-pe7[i]);
            int slotsAfter = n-1-i;
            for(int dgt=dig[i]+1; dgt<=9; dgt++){
                int[] ex = EXP[dgt];
                int ne2=Math.max(0,re2-ex[0]);
                int ne3=Math.max(0,re3-ex[1]);
                int ne5=Math.max(0,re5-ex[2]);
                int ne7=Math.max(0,re7-ex[3]);
                if(feasible(ne2,ne3,ne5,ne7,slotsAfter)){
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append((char)('0'+dgt));
                    sb.append(buildSuffix(ne2,ne3,ne5,ne7,slotsAfter));
                    return sb.toString();
                }
            }
        }

        int M = c+d+minSlots23(a,b);
        int L = Math.max(n+1, M);
        return buildSuffix(a,b,c,d,L);
    }

    private int minSlots23(int e2, int e3) {
        int q2 = e2 / 3, r2 = e2 % 3;
        int q3 = e3 / 2, r3 = e3 % 2;
        int extra;
        if (r2 == 0 && r3 == 0) extra = 0;
        else if (r2 > 0 && r3 > 0) extra = (r2 == 2) ? 2 : 1;
        else extra = 1;
        return q2 + q3 + extra;
    }

    private boolean feasible(int e2,int e3,int e5,int e7,int slots){
        long need = (long)e5+e7;
        if(need>slots) return false;
        long remaining = slots-need;
        return minSlots23(e2,e3) <= remaining;
    }

    private String buildSuffix(int e2,int e3,int e5,int e7,int k){
        StringBuilder sb=new StringBuilder();
        int ce2=e2,ce3=e3,ce5=e5,ce7=e7;
        for(int pos=0;pos<k;pos++){
            int slotsAfter = k-pos-1;
            for(int dgt=1;dgt<=9;dgt++){
                int[] ex=EXP[dgt];
                int ne2=Math.max(0,ce2-ex[0]);
                int ne3=Math.max(0,ce3-ex[1]);
                int ne5=Math.max(0,ce5-ex[2]);
                int ne7=Math.max(0,ce7-ex[3]);
                if(feasible(ne2,ne3,ne5,ne7,slotsAfter)){
                    sb.append((char)('0'+dgt));
                    ce2=ne2; ce3=ne3; ce5=ne5; ce7=ne7;
                    break;
                }
            }
        }
        return sb.toString();
    }
}