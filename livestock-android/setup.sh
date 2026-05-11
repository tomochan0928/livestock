#!/bin/bash
# Local build setup script
echo "家畜健康管理 Android APK ビルドセットアップ"
echo "============================================"

ASSETS_DIR="app/src/main/assets/www"
HTML_FILE="$ASSETS_DIR/livestock_health_v5.html"
HTML_URL="https://raw.githubusercontent.com/tomochan0928/livestock/main/livestock_health_v5.html"

mkdir -p "$ASSETS_DIR"

if [ -f "$HTML_FILE" ]; then
    echo "✅ HTMLファイルが既に存在します"
else
    echo "📥 HTMLファイルをダウンロード中..."
    if command -v curl &> /dev/null; then
        curl -L "$HTML_URL" -o "$HTML_FILE"
    elif command -v wget &> /dev/null; then
        wget -O "$HTML_FILE" "$HTML_URL"
    else
        echo "❌ curl/wget が見つかりません。手動でHTMLを配置してください"
        echo "   URL: $HTML_URL"
        echo "   配置先: $HTML_FILE"
        exit 1
    fi
    echo "✅ HTMLファイルをダウンロードしました"
fi

echo ""
echo "次のステップ:"
echo "  Android Studio でプロジェクトを開き、Build → Build APK を実行してください"
