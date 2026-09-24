package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
	private static final String helpTitle = "ヘルプ | LMS";

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		final WebElement functionButton = webDriver.findElement(By.cssSelector("a [href='#'].dropdown-toggle"));
		final WebElement helpButton = webDriver.findElement(By.cssSelector("a[href='/lms/help']"));

		functionButton.click();
		helpButton.click();

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// ログイン画面のタイトルを確認させる
		assertEquals(helpTitle, webDriver.getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加
	}

}
