package gxnu.newkids.AC.trie;

/**
 * 一个片段
 */
public abstract class Token
{
    /**
     * 对应的片段
     */
    private String fragment;

    public Token(String fragment)
    {
        this.fragment = fragment;
    }

    public String getFragment()
    {
        return this.fragment;
    }

    public abstract boolean isMatch();

    public abstract Emit getEmit();

}
