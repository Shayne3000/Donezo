import SwiftUI
import multiplatform

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        PresentationDonezoViewControllerKt.DonezoViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
	var body: some View {
		ComposeView().ignoresSafeArea(.keyboard)
	}
}
