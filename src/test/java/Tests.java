import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;


public class Tests {

    @Test
    public void getStrings() throws IOException {
        Grep grep = new Grep("-r -v word and src\\test\\java\\input1.txt");
        assertEquals("[nothing, middle woRd must be there]", grep.getStrings().toString());
        grep = new Grep("-v word src\\test\\java\\input1.txt");
        assertEquals("[nothing, anotherwordnothing, middle woRd must be there, and input1.txt for sure]",
                grep.getStrings().toString());
        grep = new Grep("-i -r -v word and src\\test\\java\\input1.txt");
        assertEquals("[nothing]", grep.getStrings().toString());
        grep = new Grep("-r word src\\test\\java\\input1.txt");
        assertEquals("[anotherwordnothing, word and another]", grep.getStrings().toString());
        grep = new Grep("-r -v word and src\\test\\java\\input2.txt");
        assertEquals("[]", grep.getStrings().toString());
        assertThrows(IllegalArgumentException.class, () -> new Grep("-r -v word and src\\test\\java\\input1.t"));
    }

}
