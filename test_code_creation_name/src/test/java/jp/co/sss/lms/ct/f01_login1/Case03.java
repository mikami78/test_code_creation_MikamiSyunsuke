package jp.co.sss.lms.ct.f01_login1;

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
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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
	private static final String title = "ログイン | LMS";

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
		assertEquals(title, webDriver.getTitle());
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

}
