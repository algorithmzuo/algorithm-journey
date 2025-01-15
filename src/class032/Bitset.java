package class032;

class Bitset {
    private int[] set;
    public int zeros;
    public int ones;
    public int size;

    public Bitset(int size) {
        set =new int[(size+31)/32];
        ones=0;
        zeros=size;
        this.size=size;
    }

    public void fix(int idx) {
        int index=idx/32;
        int offset=idx%32;
        if((set[index]>>>offset&1)==0) {
            set[index] |= 1 << offset;
            ones++;
            zeros--;
        }
    }

    public void unfix(int idx) {
        int index=idx/32;
        int offset=idx%32;
        if((set[index]>>>offset&1)!=0){
            set[index]&=~(1<<offset);
            ones--;
            zeros++;
        }
    }

    public void flip() {
        for(int i=0;i<(size+31)/32;i++){
            set[i]=~set[i];
        }
        ones=size-ones;
        zeros=size-ones;
    }

    public boolean all() {
        return size==ones;
    }

    public boolean one() {
        return ones>0;
    }

    public int count() {
        return ones;
    }

    public String toString() {
        StringBuilder sb=new StringBuilder();
        int count=0;
        for(int i=0;i<(size+31)/32;i++){
            for(int j=0;j<32&&count<size;j++,count++){
                sb.append((set[i]>>j)&1);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Bitset bitset = new Bitset(1);
        System.out.println(bitset.all());
        bitset.flip();
        System.out.println(bitset.size+" "+bitset.ones+" "+bitset.zeros);
        System.out.println(bitset.all());
    }
}