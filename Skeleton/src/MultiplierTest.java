import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiplierTest {

    int[] numbers = new int[] {
            0,10,20,300,
            3856,23915,1989,2211,10383,1307,11309,37656,42091,17323,
            15723,31117,2583,20850,14862,902,8599,11085,22682,33856,
            43155,26444,27462,6541,17022,21394,25253,25462,1224,2997,
            41529,12138,25794,21545,12324,36831,33710,1408,13457,26723,
            14924,3619,14746,19087,16825,36817,3751,25713,17752,7396,
            26727,33652,29663,905,2491,10471,28729,6582,34306,20065,
            29540,8014,7119,34480,24942,2497,607,8132,42092,28041,
            30511,13199,3570,34608,17327,24423,17831,14288,30261,38117,
            42182,12381,34931,42056,2189,41499,37744,3885,29621,40788,
            20981,7415,3199,12213,13676,12196,38440,30613,32741,36007,
            3503,35842,25683,32326,17425,11165,7266,15726,19287,4280
    };
    @Test
    void multiply() {
        for (var i : numbers)
            for (var j : numbers) {
                Word32 iw = new Word32();
                Word32 jw = new Word32();
                Word32 result = new Word32();
                TestConverter.fromInt(i,iw);
                TestConverter.fromInt(j,jw);
                Multiplier.multiply(iw,jw,result);
                assertEquals(i*j,TestConverter.toInt(result));
            }
    }

    @Test
    void multiply2() {
        Word32 x = new Word32();
        Word32 y = new Word32();
        Word32 result = new Word32();
        int a  = 5;
        int b = 5;
        TestConverter.fromInt(a,x);
        TestConverter.fromInt(b,y);
        Multiplier.multiply(x,y,result);
        assertEquals(a*b,TestConverter.toInt(result));
    }
}