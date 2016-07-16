import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class GSS1 {
	
	private static final GSS1 gss1 = new GSS1();
	private static Reader input;
	private static PrintWriter out;
	
	public class Node {
		long max_value;
		long max_prefix_value;
		long max_suffix_value;
		long sum;
		
		public String toString() {
			return max_value+"";
		}
	}

	public static void main(String[] args) throws IOException {
		input = new Reader();
		out = new PrintWriter(System.out, true);
		int N = input.nextInt();
		long[] A = new long[N];
		for(int i=0;i<N;i++) {
			A[i] = input.nextLong();
		}
		int nodes = 2*(int)Math.pow(2,log(N)) + 1;
		Node[] seg_tree = new Node[nodes];
		for(int i=0;i<nodes;i++){
			seg_tree[i] = gss1.new Node();
		}
		create_segment_tree(0,0,N-1,seg_tree,A);
		int M = input.nextInt();
		for(int i=0;i<M;i++) {
			int x = input.nextInt();
			int y = input.nextInt();
			Node val = get_query_result(0,x-1,y-1,0,N-1,seg_tree);
			out.println(val==null ? 0: val.max_value);
		}
	}

	private static void create_segment_tree(int idx, int s, int e, Node[] seg_tree, long[] a) {
		if(s==e) {
			seg_tree[idx].max_value = a[s];
			seg_tree[idx].max_prefix_value = a[s];
			seg_tree[idx].max_suffix_value = a[s];
			seg_tree[idx].sum = a[s];
		} else {
			int mid = (s+e)/2;
			create_segment_tree(2*idx+1, s, mid, seg_tree, a);
			create_segment_tree(2*idx+2, mid+1, e, seg_tree, a);
			Node left = seg_tree[2*idx+1];
			Node right = seg_tree[2*idx+2];
			merge(idx,left,right,seg_tree);
		}
	}

	private static void merge(int idx, Node left, Node right, Node[] seg_tree) {
		seg_tree[idx].max_value = Math.max(Math.max(left.max_value, right.max_value), left.max_suffix_value+right.max_prefix_value);
		seg_tree[idx].max_prefix_value = Math.max(left.max_prefix_value, left.sum+right.max_prefix_value);
		seg_tree[idx].max_suffix_value = Math.max(right.max_suffix_value, right.sum+left.max_suffix_value);
		seg_tree[idx].sum = left.sum+right.sum;
	}

	private static Node get_query_result(int idx, int s, int e, int idx_s, int idx_e, Node[] seg_tree) {
		if(e<idx_s || s>idx_e) {
			return null;
		} else if (s<=idx_s && e>=idx_e) {
			return seg_tree[idx];
		} else {
			int  mid = (idx_s + idx_e)/2;
			Node left = get_query_result(2*idx+1, s, e, idx_s, mid, seg_tree);
			Node right = get_query_result(2*idx+2, s, e, mid+1, idx_e, seg_tree);
			if(left != null && right != null) {
				return create_merged_node(left,right);
			} else if(left != null) {
				return left;
			} else if(right != null) {
				return right;
			} else {
				return null;
			}
		}
	}
	
	private static Node create_merged_node(Node left, Node right) {
		Node node = gss1.new Node();
		node.max_value = Math.max(Math.max(left.max_value, right.max_value), left.max_suffix_value+right.max_prefix_value);
		node.max_prefix_value = Math.max(left.max_prefix_value, left.sum+right.max_prefix_value);
		node.max_suffix_value = Math.max(right.max_suffix_value, right.sum+left.max_suffix_value);
		node.sum = left.sum+right.sum;
		return node;
	}

	private static int log(int n) {
		int l = 1;
		while(n>1) {
			n /= 2;
			l++;
		}
		return l;
	}
}
	
/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;
    }public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;
    }public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);
    }public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;
    }public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;
    }public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;
    }private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;
    }private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];
    }public void close() throws IOException{if(din==null) return;din.close();}
}
