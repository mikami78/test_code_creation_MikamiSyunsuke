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

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 要素を取得させる
		final WebElement detailButton = webDriver.findElement(
				By.xpath("//tr[td[contains(normalize-space(), 'Java概要')]]//input[@type='submit' and @value='詳細']"));

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
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 要素を取得させる
		final WebElement submitButton = webDriver.findElement(
				By.cssSelector("input[type='submit'][value='日報【デモ】を提出する']"));

		// 「日報【デモ】を提出する」ボタンをクリック
		submitButton.click();

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// 日報提出画面のタイトルを確認させる
		assertEquals(registReportTitle, webDriver.getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// 要素を取得させる
		final WebElement report = webDriver.findElement(By.id("content_0"));
		final WebElement submitButton = webDriver.findElement(
				By.cssSelector("button[type='submit'].btn-primary"));

		// 日報の内容を入力させる
		report.clear();
		report.sendKeys("本日はJavaの学習を行いました。");

		// 「提出する」ボタンをクリック
		submitButton.click();

		// 遷移後のセクション詳細画面で、日報のボタンが現れるまで待つ
		final WebElement submittedReportButton = new WebDriverWait(
				webDriver, Duration.ofSeconds(5))
						.until(ExpectedConditions.presenceOfElementLocated(
								By.cssSelector("form[action='/lms/report/regist'] input[type='submit']")));

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// 日報提出のボタン名を確認させる
		assertEquals(
				"提出済み日報【デモ】を確認する",
				submittedReportButton.getAttribute("value"));
	}
}
