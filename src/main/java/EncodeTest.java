import org.web3j.abi.*;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint64;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Ant
 * @Date 2022/11/5
 * @Description: 使用web3j测试编码
 */
public class EncodeTest {

    public static void main(String[] args) {
//        testSimpleEncodeAnDecode();
        testComplexEncodeAnDecode();
    }

    /**
     * 测试简单的编码和解码
     */
    private static void testSimpleEncodeAnDecode() {
        // log日志： https://goerli.etherscan.io/tx/0x39a26a8a173e783dab826c73d0c883fb376abacfc754bfd354e0d5ed3e4b9f4f#eventlog
        // 测试步骤：字符串编码成字节数组，再把字节数组解码成字符串

        String testEncodeStr = "https://did.one/image/buffalo.jpg";


        List<Type> list= Arrays.asList(new Utf8String(testEncodeStr));
        final String encodeStr = FunctionEncoder.encodeConstructor(list);

        // 打印编码后的字符串
        System.out.println(encodeStr);

        final List<Type> decodeList = FunctionReturnDecoder.decode(encodeStr,
                Utils.convert(Arrays.asList(new TypeReference<Utf8String>() {
                })));

        System.out.println(decodeList.get(0).getValue());
    }

    /**
     * 测试复杂结构的编码和解码
     */
    public static void testComplexEncodeAnDecode(){
        Function function =
                new Function(
                        "setSubnodeRecord",
                        Arrays.asList(
                                new Address("0x8ba1f109551bd432803012645ac136ddd64dba72"),
                                new Address("0xab7c8803962c0f2f5bbbe3fa8bf41cd82aa1923c"),
                                new Uint64(BigInteger.valueOf(1646904906)),
                                new Uint64(BigInteger.valueOf(120)),
                                new Utf8String("foo.verse")
                        ),
                       Arrays.asList(
                               new TypeReference<Address>(){},
                               new TypeReference<Address>(){},
                               new TypeReference<Uint64>(){},
                               new TypeReference<Uint64>(){},
                               new TypeReference<Utf8String>(){}

                       )
                );


        // 编码
        final String encodeStr = FunctionEncoder.encodeConstructor(function.getInputParameters());

        System.out.println(encodeStr);
        // 解码

        final List<Type> decodeList = FunctionReturnDecoder.decode(encodeStr,function.getOutputParameters());
        System.out.println(decodeList.get(0).getValue());
        System.out.println(decodeList.get(1).getValue());
        System.out.println(decodeList.get(2).getValue());
        System.out.println(decodeList.get(3).getValue());
        System.out.println(decodeList.get(4).getValue());


    }
}
