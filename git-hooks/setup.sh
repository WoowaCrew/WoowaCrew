# !/bin/bash
PROJECT_ROOT=`git rev-parse --show-toplevel`
rm ${PROJECT_ROOT}/.git/hooks/pre-push || true; ln -s ${PROJECT_ROOT}/git-hooks/pre-push ${PROJECT_ROOT}/.git/hooks/pre-push