import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        VStack {
            Text("SmartFridge")
                .font(.title)
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .padding()
                .background(/*@START_MENU_TOKEN@*//*@PLACEHOLDER=View@*/Color(red: 0.405, green: 0.822, blue: 0.553)/*@END_MENU_TOKEN@*/)
                Spacer()
        
        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
