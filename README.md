# Email-Clustering
The java application is for creating a database of emails by extracting data from text files, removing stopwords and stemming.
Then clustering emails using K-Means
It uses the jdbc driver to connect to a database and stores the proccessed emails in it.

The following proccessing is performed:
1. Extraction of emails from text files
    The text files must be in a format so as in the example text file.
2. Stopwords removal
    Stopwords used are stored in a file named "stopwordslist.txt"
3. Stemming
    Words are stemmed using Porter's stemming algo
4. Cosine similarity
    Cosine similarity is used as distance metric between emails to cluster them
5. K-means clustering
