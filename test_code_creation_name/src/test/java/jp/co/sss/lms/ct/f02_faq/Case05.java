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

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	private static final String faqTitle = "よくある質問 | LMS";

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
		// 要素を取得させる
		final WebElement functionButton = webDriver.findElement(By.cssSelector("a[href='#'].dropdown-toggle"));
		final WebElement helpButton = webDriver.findElement(By.cssSelector("a[href='/lms/help']"));

		// 機能ドロップダウンリストをクリック
		functionButton.click();
		// ヘルプボタンをクリック
		helpButton.click();

		// ヘルプ画面が表示されるまで待つ
		new WebDriverWait(webDriver, Duration.ofSeconds(5))
				.until(ExpectedConditions.titleIs(helpTitle));

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// ヘルプ画面のタイトルを確認させる
		assertEquals(helpTitle, webDriver.getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 現在のタブのウィンドウハンドルを取得する
		String currentWindow = webDriver.getWindowHandle();

		// 要素を取得させる
		final WebElement faqLink = webDriver.findElement(By.cssSelector("a[href='/lms/faq']"));

		// faqリンクをクリック
		faqLink.click();

		// ウィンドウタブが2つになるまで待つ
		new WebDriverWait(webDriver, Duration.ofSeconds(5))
				.until(ExpectedConditions.numberOfWindowsToBe(2));

		// 現在のタブ以外のハンドルを取得して切り替える
		for (String newWindow : webDriver.getWindowHandles()) {
			if (!newWindow.equals(currentWindow)) {
				webDriver.switchTo().window(newWindow);
				break;
			}
		}

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// 別タブで開かれたよくある質問画面のタイトルを確認させる
		assertEquals(faqTitle, webDriver.getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 要素を取得させる
		final WebElement keywordSearch = webDriver.findElement(By.id("form"));
		final WebElement searchButton = webDriver.findElement(By.cssSelector("input[type='submit']"));
		final By searchResults = By.cssSelector("table tbody tr");
		final By question = By.cssSelector("dt.mb10");
		final By answer = By.cssSelector("dd.fs18");
		final String keyword = "申請";

		// キーワードフォームをクリアしてからキーワードを入力する
		keywordSearch.clear();
		keywordSearch.sendKeys(keyword);

		// 検索ボタンをクリック
		searchButton.click();

		// 検索結果が画面上に表示されるまで待つ
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchResults, 0));

		// 質問・回答の文章から検索した結果が画面上に表示されているかを確認する
		for (WebElement faqRow : webDriver.findElements(searchResults)) {
			WebDriverUtils.scrollBy("400");

			WebElement questionElement = faqRow.findElement(question);

			wait.until(ExpectedConditions.elementToBeClickable(questionElement)).click();

			wait.until(ExpectedConditions.visibilityOf(
					faqRow.findElement(answer)));

			assertTrue(faqRow.getText().contains(keyword));
		}

		// エビデンス（スクリーンショット）を取る
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// 要素を取得させる
		final WebElement keywordSearch = webDriver.findElement(By.id("form"));
		final WebElement clearButton = webDriver.findElement(By.cssSelector("input[type='button']"));

		// クリアボタンをクリック
		clearButton.click();

		// エビデンス(スクリーンショット)を取る
		getEvidence(new Object() {
		});

		// クリア後のフォームに文字列が入力されていないかを確認させる
		assertEquals("", keywordSearch.getAttribute("value"));
	}

}
