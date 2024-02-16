import java.util.StringTokenizer;

public class Wordcounter {
    public static class WordCount {
        private String word;
        private int count;

        public WordCount(String word) {
            this.word = word;
            this.count = 1; // Initialize count to 1 for a new word.
        }

        public String getWord() {
            return word;
        }

        public int getCount() {
            return count;
        }

        public void incrementCount() {
            count++;
        }
    }

    private static final int DEFAULT_CAPACITY = 10;
    private static final double LOAD_FACTOR = 0.7;

    private WordCount[] table;
    private int size;
    private int capacity;

    public Wordcounter()  // O(1) - Constant time
    {
        capacity = DEFAULT_CAPACITY;
        table = new WordCount[capacity];
        size = 0;
    }

    private int hash(String key) {
        return (key.hashCode() & 0x7FFFFFFF) % capacity;
    }

    private void resize() 	 // O(n) - Linear time (where n is the number of elements in the current table)
    {
        int newCapacity = capacity * 2;
        WordCount[] newTable = new WordCount[newCapacity];
        for (WordCount wordCount : table) {
            if (wordCount != null) {
                int index = hash(wordCount.getWord()) % newCapacity;
                while (newTable[index] != null) {
                    index = (index + 1) % newCapacity;
                }
                newTable[index] = wordCount;
            }
        }
        table = newTable;
        capacity = newCapacity;
    }

    public void put(String word)		//O(1)
{
        if ((double) (size + 1) / capacity > LOAD_FACTOR) {
            resize();
        }
        int index = hash(word);
        while (table[index] != null) {
            if (table[index].getWord().equals(word)) {
                table[index].incrementCount();
                return;
            }
            index = (index + 1) % capacity;
        }
        table[index] = new WordCount(word);
        size++;
    }

    public int get(String word)			//O(1)
     {
        int index = hash(word);
        while (table[index] != null) {
            if (table[index].getWord().equals(word)) {
                return table[index].getCount();
            }
            index = (index + 1) % capacity;
        }
        return 0; // Word not found.
    }

    public static void main(String[] args) {
        String paragraph = "The 3 cats jumped over the $50 fence, and they landed on the 2.5-meter platform!"
        		+ "The #Brown_Fox_42 was watching from a distance, and he said, 'Hello, world!' "
        		+ "The equation 2 + 2 = 4 was written on the wall, and nearby, there was a % sign hanging from a tree.";
        StringTokenizer tokenizer = new StringTokenizer(paragraph);

        Wordcounter wordFrequencyCounter = new Wordcounter();

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            wordFrequencyCounter.put(word);
        }

        System.out.println("Word Frequency Table:"); 		//O(1)
        for (WordCount wordCount : wordFrequencyCounter.table) {
            if (wordCount != null) {
            	System.out.printf("Word: %-10s\t Length: %-2d\t Frequency: %-2d times%n",
                        wordCount.getWord(), wordCount.getWord().length(), wordCount.getCount());
            }
        }
    }
}