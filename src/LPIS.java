import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LPIS {
	
	private static Reader input;
	private static PrintWriter out;

	public static void main(String[] args) throws IOException {
		input = new LPIS().new Reader();
		out = new PrintWriter(System.out, true);
		
		int n = input.nextInt();
		int[] arr = new int[n];
		ArrayList<ArrayList<Integer>> posList = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<1000001;i++) posList.add(new ArrayList<Integer>());
		for(int i=0;i<n;i++) {
			arr[i] = input.nextInt();
			ArrayList<Integer> list = posList.get(arr[i]);
			list.add(i);
		}
		out.print(lpis(arr,n,posList));
		out.flush();
	}
	
	private static int lpis(int[] arr, int n, ArrayList<ArrayList<Integer>> posList) {
		int[] dp = new int[n];
		int max = 1;
		for(int i=0;i<n;i++) {
			int prevIdx = getPreviousIndex(i,arr[i]-1,posList);
			dp[i] = prevIdx == -1 ? 1: dp[prevIdx]+1;
			max = Math.max(max, dp[i]);
		}
		return max;
	}

	private static int getPreviousIndex(int i, int num, ArrayList<ArrayList<Integer>> posList) {
		ArrayList<Integer> list = posList.get(num);
		if(!list.isEmpty()) {
			return search(0,list.size()-1,i,list);
		}
		return -1;
	}

	private static int search(int s, int e, int idx, ArrayList<Integer> list) {
		if(idx < list.get(s)) return -1;
		if(idx > list.get(e)) return list.get(e);
		int mid = (s+e)/2;
		if(list.get(mid) < idx) {
			if(list.get(mid+1) > idx) {
				return list.get(mid);
			} else {
				return search(mid+1, e, idx, list);
			}
		} else {
			return search(s, mid, idx, list);
		}
	}

	/** Faster input **/
	class Reader {
	    final private int BUFFER_SIZE = 1 << 16;
	    private DataInputStream din;
	    private byte[] buffer;
	    private int bufferPointer, bytesRead;
	    public Reader(){
	        din=new DataInputStream(System.in);
	        buffer=new byte[BUFFER_SIZE];
	        bufferPointer=bytesRead=0;
	    }

	    public Reader(String file_name) throws IOException{
	        din=new DataInputStream(new FileInputStream(file_name));
	        buffer=new byte[BUFFER_SIZE];
	        bufferPointer=bytesRead=0;
	    }

	    public String readLine() throws IOException{
	        byte[] buf=new byte[64]; // line length
	        int cnt=0,c;
	        while((c=read())!=-1){
	            if(c=='\n')break;
	            buf[cnt++]=(byte)c;
	        }
	        return new String(buf,0,cnt);
	    }

	    public int nextInt() throws IOException{
	        int ret=0;byte c=read();
	        while(c<=' ')c=read();
	        boolean neg=(c=='-');
	        if(neg)c=read();
	        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
	        if(neg)return -ret;
	        return ret;
	    } 

	    public long nextLong() throws IOException{
	        long ret=0;byte c=read();
	        while(c<=' ')c=read();
	        boolean neg=(c=='-');
	        if(neg)c=read();
	        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
	        if(neg)return -ret;
	        return ret;
	    }

	    public double nextDouble() throws IOException{
	        double ret=0,div=1;byte c=read();
	        while(c<=' ')c=read();
	        boolean neg=(c=='-');
	        if(neg)c = read();
	        do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
	        if(c=='.')while((c=read())>='0'&&c<='9')
	            ret+=(c-'0')/(div*=10);
	        if(neg)return -ret;
	        return ret;
	    }
	    
	    private void fillBuffer() throws IOException{
	        bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);
	        if(bytesRead==-1)buffer[0]=-1;
	    }
	    
	    private byte read() throws IOException{
	        if(bufferPointer==bytesRead)fillBuffer();
	        return buffer[bufferPointer++];
	    }
	    
	    public void close() throws IOException{
	        if(din==null) return;
	        din.close();
	    }
	}

}
