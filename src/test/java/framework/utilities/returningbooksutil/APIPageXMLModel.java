package framework.utilities.returningbooksutil;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "feed")
public class APIPageXMLModel {
    private List<Entry> entries;

    public List<Entry> getEntries() {
        return entries;
    }

    @XmlElement(name = "entry")
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}

class Entry {
    private List<Link> links;
    private String title;
    private Distributor distributor;

    public Distributor getDistributor() {
        return distributor;
    }

    @XmlElement(name = "distribution")
    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Link> getLinks() {
        return links;
    }

    @XmlElement(name = "link")
    public void setLinks(List<Link> links) {
        this.links = links;
    }
}

class Distributor {
    private String distributorName;

    public String getDistributorName() {
        return distributorName;
    }

    @XmlAttribute(name = "ProviderName", namespace = "http://bibframe.org/vocab/")
    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }
}

class Link {
    private String href;

    public String getHref() {
        return href;
    }

    @XmlAttribute(name = "href")
    public void setHref(String href) {
        this.href = href;
    }
}
