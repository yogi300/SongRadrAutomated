package com.songtradr.metadata.pojo;

public class Product {
	private Product_items product_items;

	private String languagepr;

	private Copyright copyright;

	private String preorder_prelistening;

	private String explicit_content;

	private Release release;

	private String ean_code;

	private String gapless;

	private String promo_activ;

	private Product_title product_title;

	private String preorder_date_selection;

	private String total_tracks;

	private String media_type;

	private String preorder_date;

	private Product_artist product_artist;

	private Pline pline;

	private Genre genre;

	private String price_type;

	private Track[] track;

	private String dod_active;

	private String label_id;

	private String productno;

	private String preorder;

	public Product_items getProduct_items() {
		return product_items;
	}

	public void setProduct_items(Product_items product_items) {
		this.product_items = product_items;
	}

	public String getLanguagepr() {
		return languagepr;
	}

	public void setLanguagepr(String languagepr) {
		this.languagepr = languagepr;
	}

	public Copyright getCopyright() {
		return copyright;
	}

	public void setCopyright(Copyright copyright) {
		this.copyright = copyright;
	}

	public String getPreorder_prelistening() {
		return preorder_prelistening;
	}

	public void setPreorder_prelistening(String preorder_prelistening) {
		this.preorder_prelistening = preorder_prelistening;
	}

	public String getExplicit_content() {
		return explicit_content;
	}

	public void setExplicit_content(String explicit_content) {
		this.explicit_content = explicit_content;
	}

	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public String getEan_code() {
		return ean_code;
	}

	public void setEan_code(String ean_code) {
		this.ean_code = ean_code;
	}

	public String getGapless() {
		return gapless;
	}

	public void setGapless(String gapless) {
		this.gapless = gapless;
	}

	public String getPromo_activ() {
		return promo_activ;
	}

	public void setPromo_activ(String promo_activ) {
		this.promo_activ = promo_activ;
	}

	public Product_title getProduct_title() {
		return product_title;
	}

	public void setProduct_title(Product_title product_title) {
		this.product_title = product_title;
	}

	public String getPreorder_date_selection() {
		return preorder_date_selection;
	}

	public void setPreorder_date_selection(String preorder_date_selection) {
		this.preorder_date_selection = preorder_date_selection;
	}

	public String getTotal_tracks() {
		return total_tracks;
	}

	public void setTotal_tracks(String total_tracks) {
		this.total_tracks = total_tracks;
	}

	public String getMedia_type() {
		return media_type;
	}

	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}

	public String getPreorder_date() {
		return preorder_date;
	}

	public void setPreorder_date(String preorder_date) {
		this.preorder_date = preorder_date;
	}

	public Product_artist getProduct_artist() {
		return product_artist;
	}

	public void setProduct_artist(Product_artist product_artist) {
		this.product_artist = product_artist;
	}

	public Pline getPline() {
		return pline;
	}

	public void setPline(Pline pline) {
		this.pline = pline;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getPrice_type() {
		return price_type;
	}

	public void setPrice_type(String price_type) {
		this.price_type = price_type;
	}

	public Track[] getTrack() {
		return track;
	}

	public void setTrack(Track[] track) {
		this.track = track;
	}

	public String getDod_active() {
		return dod_active;
	}

	public void setDod_active(String dod_active) {
		this.dod_active = dod_active;
	}

	public String getLabel_id() {
		return label_id;
	}

	public void setLabel_id(String label_id) {
		this.label_id = label_id;
	}

	public String getProductno() {
		return productno;
	}

	public void setProductno(String productno) {
		this.productno = productno;
	}

	public String getPreorder() {
		return preorder;
	}

	public void setPreorder(String preorder) {
		this.preorder = preorder;
	}

	@Override
	public String toString() {
		return "ClassPojo [product_items = " + product_items + ", languagepr = " + languagepr + ", copyright = "
				+ copyright + ", preorder_prelistening = " + preorder_prelistening + ", explicit_content = "
				+ explicit_content + ", release = " + release + ", ean_code = " + ean_code + ", gapless = " + gapless
				+ ", promo_activ = " + promo_activ + ", product_title = " + product_title
				+ ", preorder_date_selection = " + preorder_date_selection + ", total_tracks = " + total_tracks
				+ ", media_type = " + media_type + ", preorder_date = " + preorder_date + ", product_artist = "
				+ product_artist + ", pline = " + pline + ", genre = " + genre + ", price_type = " + price_type
				+ ", track = " + track + ", dod_active = " + dod_active + ", label_id = " + label_id + ", productno = "
				+ productno + ", preorder = " + preorder + "]";
	}
}
