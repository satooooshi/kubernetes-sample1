#!/usr/bin/env bash
set -eu

cd "$(dirname $0)"

export ARTICLE_URL="${ARTICLE_URL:-http://localhost:8081}"
echo "hellool"
#export ARTICLE_URL=http://172.22.116.93:46871
#export CURL_OPTS='-s'
export CURL_OPTS='-sS'
export TEMPLATE_FILE=./article-body.template
function sample_body() {
    if type envsubst &> /dev/null; then
        export suffix="$1"
        envsubst < "${TEMPLATE_FILE}"
        exit 0
    fi
    sed -r "s/\\\$\\{suffix\\}/$1/g" "${TEMPLATE_FILE}"
}
export TITLE_BASES=( \
    "グラフデータベースライブラリの紹介" \
    "Kubernetesバージョン1.21について知っておきたいこと" \
    "VCSなんでも相談会のご案内" \
    "ネットワークライブラリを使ったアプリケーションの作り方" \
    "富山発 共創事業の紹介" \
    "2021年に注目すべきクラウドネイティブセキュリティのトレンド" \
    "グラフアルゴリズムを活用した転帰の向上" \
    "NoSQLデータベースを運用する - CI/CDの価値 -" \
    "VCM バージョン13 アップデートニュースレター" \
    "コンテナイメージとKubernetesにおけるデバッグプロトコルの設定ミス" \
    "フルマネージドNoSQLサービスへのデータの移行について" \
    "脅威: 開発環境を狙ったクリプトマイニング" \
    "コンソール作業チートシート簡易版" \
    "軽量Kubernetesのセットアップ: 簡易版" \
    "クラウドネイティブにおけるフォレンジック: その課題とベストプラクティス" \
)


export title_random_shift="${RANDOM}"
for i in {1..10}; do
    #echo "${ARTICLE_URL}/api/articles/"
    suffix="$(echo -n "${i}-$(date --iso-8601=ns)" | md5sum | base64 | sed -r -e 's/[^a-zA-Z]+//g' | head -c4)"
    suffix="${suffix,,}"
    title="(${suffix})${TITLE_BASES[$(( ( ${i} + ${title_random_shift} ) % ${#TITLE_BASES[@]} ))]}"
    author="John ${suffix^} Doe"
    #body="Very interest article here. (${suffix})"
    body="$(sample_body ${suffix})"
    body="$(echo "${body}" | sed -r -e 's/"/\\"/g' -e 's/$/\\n/' | tr -d '\n')"
    curl \
        ${CURL_OPTS} \
        -H 'Content-Type: application/json' \
        --data-binary '{"title":"'"${title}"'","author":"'"${author}"'","body":"'"${body}"'"}' \
        "${ARTICLE_URL}/api/articles/"
done
