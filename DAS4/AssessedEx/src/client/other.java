package client;

public class other {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long[] startL = new long[5];
		int[] startI = new int[5];

		for (int i = 0; i < 5; i++) {
			startL[i] = i;
			startI[i] = i < 3 ? 1 : 2;
		}

		long article_id;
		int count;

		long[] articles = new long[5];
		int[] counts = new int[5];

		for (int a = 0; a < articles.length; a++) {
			articles[a] = -1;
			counts[a] = -1;
		}

		for (int a = 0; a < startI.length; a++) {
			article_id = startL[a];
			count = startI[a];

			for (int i = 0; i < counts.length; i++) {
				if (counts[i] < count) {
					for (int j = counts.length - 1; j > i; j--) {
						System.out.println(j);
						counts[j] = counts[j - 1];
						articles[j] = articles[j - 1];
					}
					counts[i] = count;
					articles[i] = article_id;
					break;
				}
				if (counts[i] == count) {
					for (; i < articles.length; i++) {
						if (article_id < articles[i] || articles[i] == -1
								|| counts[i] < count) {
							for (int j = counts.length - 1; j > i; j--) {
								counts[j] = counts[j - 1];
								articles[j] = articles[j - 1];
							}
							counts[i] = count;
							articles[i] = article_id;
							break;
						}
					}
					break;
				}
			}

			for (int b = 0; b < counts.length; b++) {
				System.out.println(articles[b] + " " + counts[b]);
			}
			System.out.println();

		}

		for (int a = 0; a < counts.length; a++) {
			System.out.println(articles[a] + " " + counts[a]);
		}

	}
}
