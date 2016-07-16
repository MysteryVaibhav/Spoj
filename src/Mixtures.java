import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Mixtures {
	
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

	public static void main(String[] args) throws NumberFormatException, IOException {
		String line;
        while ((line = in.readLine()) != null) {
        	int n = Integer.parseInt(line);
			int[] mixture = new int[n+2];
			int[] sum = new int[n+2];
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			for(int i=1;i<=n;i++) {
				mixture[i] = Integer.parseInt(tokenizer.nextToken());
				sum[i] = mixture[i] + sum[i-1];
			}
			out.println(minSmoke(mixture,n,sum));
		}
        out.flush();
	}

	private static int minSmoke(int[] mixture, int n, int[] sum) {
		int[][] dp = new int[n+2][n+2];
		for(int i=n;i>0;i--) {
			for(int j=i+1;j<=n;j++) {
				dp[i][j] = Integer.MAX_VALUE;
				for(int k=i;k<=j;k++) {
					int left = (sum[k] - sum[i-1])%100;
					int right = (sum[j] - sum[k])%100;
					dp[i][j] = Math.min(left*right + dp[i][k] + dp[k+1][j], dp[i][j]);
				}
			}
		}
		return dp[1][n];
	}

}
