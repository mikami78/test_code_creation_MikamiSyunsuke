package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	private static final String url = "http://localhost:8080/lms/";
	private static final String loginTitle = "ログイン | LMS";
	private static final String detailTitle = "セクション詳細 | LMS";
	private static final String registReportTitle = "レポート登録 | LMS";
	private static final String userDetailTitle = "ユーザー詳細";

	@Test
	@Order(1)
	@DisplayName("テスト01 ログイン画面のタイトルの確認")
	void test01() {
		// 下記URLのページにアクセスさせる
		webDriver.get(url);

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// ログイン画面のタイトルを確認させる
		assertEquals(loginTitle, webDriver.getTitle());
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// 要素を取得させる
		final WebElement loginId = webDriver.findElement(By.id("loginId"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement loginButton = webDriver.findElement(By.cssSelector("input[type='submit']"));
		final By welcomeMessage = By.cssSelector("small");

		// ログインIDフォームをクリアしてからログインIDを入力する
		loginId.clear();
		loginId.sendKeys("StudentAA01");

		// パスワードフォームをクリアしてからパスワードを入力する
		password.clear();
		password.sendKeys("StudentAA001");

		// ログインボタンをクリック
		loginButton.click();

		// ログインメッセージが画面上に表示されるまで待つ
		final WebElement loginElement = new WebDriverWait(
				webDriver, Duration.ofSeconds(5))
						.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage));

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// ログインメッセージが画面上に表示されているかを確認させる
		assertTrue(loginElement.getText().contains("ようこそ受講生ＡＡ１さん"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 要素を取得させる
		final WebElement detailButton = webDriver.findElement(
				By.xpath(
						"//tr[td[contains(normalize-space(), 'アルゴリズム、フローチャート')]]//input[@type='submit' and @value='詳細']"));

		// 「詳細」ボタンをクリック
		detailButton.click();

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// セクション詳細画面のタイトルを確認させる
		assertEquals(detailTitle, webDriver.getTitle());

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 要素を取得させる
		final WebElement submitButton = webDriver.findElement(
				By.cssSelector("input[type='submit'][value='提出済み週報【デモ】を確認する']"));

		// 画面をスクロール
		WebDriverUtils.scrollBy("300");

		// 「週報【デモ】を確認する」ボタンをクリック
		submitButton.click();

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// 週報提出画面のタイトルを確認させる
		assertEquals(registReportTitle, webDriver.getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 要素を取得させる
		final WebElement report_Achievement = webDriver.findElement(By.id("content_0"));
		final WebElement report_Impressions = webDriver.findElement(By.id("content_1"));
		final WebElement report_Review = webDriver.findElement(By.id("content_2"));
		final WebElement submitButton = webDriver.findElement(
				By.cssSelector("button[type='submit'].btn-primary"));

		// 週報の内容を入力させる
		report_Achievement.clear();
		report_Achievement.sendKeys("4");

		report_Impressions.clear();
		report_Impressions.sendKeys("週報の内容を修正します。");

		report_Review.clear();
		report_Review.sendKeys("今週はできました。");

		// 「提出する」ボタンをクリック
		submitButton.click();

		// セクション詳細画面のタイトルになるまで待つ
		new WebDriverWait(webDriver, Duration.ofSeconds(5))
				.until(ExpectedConditions.titleIs(detailTitle));

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// セクション詳細画面のタイトルを確認させる
		assertEquals(detailTitle, webDriver.getTitle());

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// 要素を取得させる
		final WebElement userDetailButton = webDriver.findElement(By.cssSelector("a[href='/lms/user/detail']"));

		// ユーザー詳細リンクをクリック
		userDetailButton.click();

		// ユーザー詳細画面が表示されるまで待つ
		new WebDriverWait(webDriver, Duration.ofSeconds(5))
				.until(ExpectedConditions.titleIs(userDetailTitle));

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// ユーザー詳細画面のタイトルを確認させる
		assertEquals(userDetailTitle, webDriver.getTitle());

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		final By reportDetailButton = By.xpath(
				"//tr[td[contains(normalize-space(.), '週報【デモ】')]]//input[@type='submit' and @value='詳細']");

		// 画面をスクロール
		WebDriverUtils.scrollBy("700");

		// 「詳細」ボタンがクリック可能になるまで待つ
		final WebElement reportDetail = new WebDriverWait(webDriver, Duration.ofSeconds(5))
				.until(ExpectedConditions.elementToBeClickable(reportDetailButton));

		// 「詳細」ボタンをクリック
		reportDetail.click();

		// 画面遷移後、修正内容が表示されるまで待つ
		new WebDriverWait(webDriver, Duration.ofSeconds(5))
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[contains(text(), '週報の内容を修正します。')]")));

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// 週報の修正内容を確認させる
		assertEquals("4", webDriver.findElement(
				By.xpath("//tr[th[normalize-space()='目標の達成度']]/td")).getText().trim());

		assertEquals("週報の内容を修正します。", webDriver.findElement(
				By.xpath("//tr[th[normalize-space()='所感']]/td")).getText().trim());

		assertEquals("今週はできました。", webDriver.findElement(
				By.xpath("//tr[th[normalize-space()='一週間の振り返り']]/td")).getText().trim());
	}

}
