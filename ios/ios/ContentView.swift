import SwiftUI
import Multiplatform

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        DonezoViewControllerKt.DonezoViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
	var body: some View {
		ComposeView().ignoresSafeArea(.keyboard)
	}
}
