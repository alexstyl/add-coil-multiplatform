./gradlew jsBrowserDistribution

project_root=$(pwd)

demo_source="${project_root}/demo/build/dist/js/productionExecutable/*"
demo_destination="${project_root}/website"

mkdir -p "$demo_destination"

mv $demo_source "$demo_destination"