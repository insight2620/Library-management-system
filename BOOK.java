//图书实体
public class BOOK {
    //属性（成员变量）
    private String isbn;
    private String class1;
    private String subclass;
    private String name;
    private String author;
    private String price;
    private String pubdate;
    private String introduction;

    //构造函数
    public String getisbn() { return isbn; }
    public void setisbn(String isbn) { this.isbn = isbn; }
    public String getclass1() {return class1; }
    public void setclass1(String class1) { this.class1 = class1; }
    public String getsubclass() { return subclass; }
    public void setsubclass(String subclass) { this.subclass = subclass; }
    public String getname() { return name; }
    public void setname(String name) { this.name = name; }
    public String getauthor() { return author; }
    public void setauthor(String author) { this.author = author; }
    public String getprice() { return price; }
    public void setprice(String price) { this.price = price; }
    public String getpubdate() { return pubdate; }
    public void setpubdate(String pubdate) { this.pubdate = pubdate; }
    public String getintroduction() { return introduction; }
    public void setintroduction(String introduction) { this.introduction = introduction; }

    public BOOK(String isbn, String class1, String subclass, String name, String author,String price,String pubdate,String introduction)
    {
        super();
        this.isbn = isbn;
        this.class1 = class1;
        this.subclass = subclass;
        this.name = name;
        this.author = author;
        this.price = price;
        this.pubdate = pubdate;
        this.introduction = introduction;
    }
    
}
