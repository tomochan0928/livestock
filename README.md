# 家畜健康管理 Android アプリ

livestock_health_v5.html をオフラインで動作するAndroidアプリ（APK）にパッケージ化したプロジェクトです。

## 🚀 APKの取得方法（推奨: GitHub Actionsを使用）

### 方法1: GitHubにプッシュして自動ビルド（最も簡単）

1. このフォルダの内容を `tomochan0928/livestock` リポジトリに追加します

   ```bash
   cd /path/to/livestock  # 既存のlivstockリポジトリ
   cp -r /path/to/livestock-android/* .
   git add .
   git commit -m "Add Android app project"
   git push origin main
   ```

2. GitHub Actions が自動的にAPKをビルドします
   - リポジトリの「Actions」タブで進捗を確認
   - ビルド完了後、「Releases」からAPKをダウンロード

### 方法2: Android Studioでローカルビルド

1. **Android Studioをインストール** (https://developer.android.com/studio)

2. **HTMLファイルを配置**
   ```bash
   ./setup.sh
   ```
   または手動で:
   ```
   app/src/main/assets/www/livestock_health_v5.html
   ```
   に元のHTMLファイルをコピー

3. **Android Studioでプロジェクトを開く**
   このフォルダを「Open」

4. **APKをビルド**
   メニュー: `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`

5. **APKの場所**
   `app/build/outputs/apk/debug/app-debug.apk`

## 📱 アプリの機能

- 🏥 **完全オフライン動作**（ネットワーク不要）
- 💾 **データはスマートフォン内に保存**（localStorage）
- 📷 **カメラ・バーコードスキャン**対応
- 📊 **Excel形式でのエクスポート**
- 🔖 **耳標スキャン機能**
- 💊 **薬剤プリセット管理**
- 📝 **診療記録管理**
- 🏠 **往診先管理**

## 📋 必要要件

- Android 7.0 (API 24) 以上
- カメラ権限（バーコードスキャン・写真機能に必要）

## ⚠️ インストール時の注意

APKをインストールする際は、設定で「提供元不明のアプリ」または「不明なアプリのインストール」を許可する必要があります。

## 🔧 プロジェクト構成

```
livestock-android/
├── .github/workflows/build-apk.yml  # 自動ビルド設定
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── assets/www/              # HTMLファイルを配置
│   │   ├── java/com/livestock/health/
│   │   │   └── MainActivity.kt     # WebViewアクティビティ
│   │   └── res/
├── build.gradle
├── settings.gradle
└── setup.sh                        # ローカルビルド用セットアップ
```
