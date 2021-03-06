package t1;

import java.util.*;

public class T1_Solution {
	
	/**
	 * @author yukunlee
	 * @Description Dijkstra-algorithm 迪杰斯特拉算法 
	 * @date 2018年12月11日
	 * @param times
	 * @param N
	 * @param K
	 * @return
	 */
	public int[] DijkstraAlgorithm(int[][] times, int N, int K) {
		Map<Integer, ArrayList<int[]>> graph = new HashMap<>();
		for (int[] edge : times) {
			graph.putIfAbsent(edge[0], new ArrayList<>());
			graph.get(edge[0]).add(new int[] { edge[1], edge[2] });
		}
		//Collection<? extends PriorityQueue<int[]>> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
		Stack<PriorityQueue<int[]>> st = new Stack<PriorityQueue<int[]>>();
		//Queue<PriorityQueue<int[]>> queue1 = new LinkedList<PriorityQueue<int[]>>(pq);
		int[] distence = new int[N + 1];
		boolean[] visited = new boolean[N + 1];
		
		pq.add(new int[] { 0, K });
		st.push(pq);
		while(!st.isEmpty()) {
			PriorityQueue<int[]> cur_queue = st.peek();
			PriorityQueue<int[]> new_queue = new PriorityQueue<>();
			int flag = 0;
			while(!cur_queue.isEmpty()) {
				int[] cur = cur_queue.poll();
				if (visited[cur[1]])continue;
				visited[cur[1]] = true;
				flag = 1;
				for (int[] next : graph.get(cur[1])) {
					new_queue.offer(new int[] {cur[0]+next[1], next[0] });
					distence[next[0]] = Math.min(cur[0]+next[1], distence[next[0]]);
				}
				break;
			}
			if(flag == 0 ||new_queue.isEmpty()) {
				st.pop();
				continue;
			}
			st.push(new_queue);
		}
		
		return distence;
	}
	
	/**
	 * @author yukunlee
	 * @Description Bellman-Ford algorithm
	 * @date 2018年12月13日
	 * @param times
	 * @param N
	 * @param K
	 * @return
	 */
	public int BellmanFordalgorithm(int[][] times, int N, int K) {
		double[] distence = new double[N];
		Arrays.fill(distence, Double.POSITIVE_INFINITY);
		distence[K-1] = 0;
		for(int i = 0 ; i < N ; ++i) {
			for(int[] edge : times) {
				distence[edge[1]-1] = Math.min(distence[edge[1]-1], distence[0]+edge[2]);
			}
		}
		double res = Double.MIN_VALUE;
		for(double i : distence) {
			res = Math.max(res, i);
		}
		return res == Double.POSITIVE_INFINITY? -1 : (int)res ;
	}

	
	/**
	 * @author yukunlee
	 * @Description  leetcode 743. Network Delay Time apply Dijkstra-algorithm
	 * @date 2018年12月10日
	 * @param times
	 * @param N
	 * @param K
	 * @return
	 */
	public int networkDelayTime(int[][] times, int N, int K) {
		Map<Integer, ArrayList<int[]>> graph = new HashMap<>();
		int[] distence = new int[N + 1];
		boolean[] visited = new boolean[N + 1];
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
		// Queue<Integer> q = new LinkedList<>();
		for (int[] edge : times) {
			graph.putIfAbsent(edge[0], new ArrayList<>());
			graph.get(edge[0]).add(new int[] { edge[1], edge[2] });
		}
		Arrays.fill(distence, Integer.MAX_VALUE);
		distence[K] = 0;

		int max = 0;
		pq.add(new int[] { 0, K });
		while (!pq.isEmpty()) {
			int[] cur = pq.poll();
			int curNode = cur[1];
			if (visited[curNode])
				continue;
			visited[curNode] = true;
			int curDis = cur[0];
			max = curDis;
			--N;
			if (!graph.containsKey(curNode))
				continue;
			for (int[] next : graph.get(curNode)) {
				if (!visited[next[0]] && curDis + next[1] < distence[next[0]]) {
					pq.offer(new int[] { curDis + next[1], next[0] });
					//distence[next[0]] = curDis + next[1];
				}
			}
		}
		return N == 0 ? max : -1;
	}

}
